package io.github.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.hr.model.MenuDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author John
 * @Date 2020/8/29下午8:49
 */
@Repository
public interface MenuMapper extends BaseMapper<MenuDO> {
}
