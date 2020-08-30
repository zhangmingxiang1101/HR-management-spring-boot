package io.github.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.hr.model.RoleDO;
import org.springframework.stereotype.Repository;

/**
 * @Author John
 * @Date 2020/8/29下午8:38
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleDO> {
    RoleDO getOne();
}
