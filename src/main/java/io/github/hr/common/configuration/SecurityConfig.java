package io.github.hr.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hr.common.util.HrUtils;
import io.github.hr.model.RespBean;
import io.github.hr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.io.PrintWriter;

/**
 * @Author John
 * @Date 2020/8/29下午10:11
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;
    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    CustomUrlAccessDecisionManager urlManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(metadataSource);
                        object.setAccessDecisionManager(urlManager);
                        return object;
                    }
                })
                .and()
                .formLogin().loginPage("login_p").loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password")
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    RespBean respBean = null;
                    if (exception instanceof LockedException) {
                        respBean.setMsg("账户被锁定，请联系管理员!");
                    } else if (exception instanceof CredentialsExpiredException) {
                        respBean.setMsg("密码过期，请联系管理员!");
                    } else if (exception instanceof AccountExpiredException) {
                        respBean.setMsg("账户过期，请联系管理员!");
                    } else if (exception instanceof DisabledException) {
                        respBean.setMsg("账户被禁用，请联系管理员!");
                    } else if (exception instanceof BadCredentialsException) {
                        respBean.setMsg("用户名或者密码输入错误，请重新输入!");
                    } else {
                        respBean.setMsg("登陆失败！");
                    }
                    response.setStatus(401);
                    ObjectMapper objectMapper = new ObjectMapper();
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    RespBean respBean = RespBean.ok("登陆成功!", HrUtils.getCurrentHr());
                    ObjectMapper objectMapper = new ObjectMapper();
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(respBean));
                    out.flush();
                    out.close();
                })
                .permitAll()
                .and()
                .logout().permitAll()
                .and().csrf().disable().exceptionHandling()
                .authenticationEntryPoint((req, resp, authException) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            resp.setStatus(401);
                            PrintWriter out = resp.getWriter();
                            RespBean respBean = RespBean.error("访问失败!");
                            if (authException instanceof InsufficientAuthenticationException) {
                                respBean.setMsg("请求失败，请联系管理员!");
                            }
                            out.write(new ObjectMapper().writeValueAsString(respBean));
                            out.flush();
                            out.close();
                        }
                );
    }
}
