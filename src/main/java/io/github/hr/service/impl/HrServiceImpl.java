package io.github.hr.service.impl;

import io.github.hr.mapper.HrMapper;
import io.github.hr.model.HrDO;
import io.github.hr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author John
 * @Date 2020/8/29下午8:07
 */
@Service
public class HrServiceImpl implements HrService {

    @Autowired
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HrDO hrDO = hrMapper.loadUserByUsername(s);
        if (hrDO == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return hrDO;
    }
}
