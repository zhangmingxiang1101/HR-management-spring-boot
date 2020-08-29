package io.github.hr.service.impl;

import io.github.hr.mapper.MenuMapper;
import io.github.hr.model.MenuDO;
import io.github.hr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author John
 * @Date 2020/8/29下午8:51
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<MenuDO> getAllMenu() {
        return menuMapper.selectList(null);
    }
}
