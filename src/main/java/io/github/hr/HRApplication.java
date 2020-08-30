package io.github.hr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author  John
 * @Date 2020/8/29下午7:44
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.hr.mapper")
public class HRApplication {

    public static void main(String[] args) {
        SpringApplication.run(HRApplication.class, args);
    }
}
