package io.github.hr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.hr.model.HrDO;
import org.springframework.stereotype.Repository;

/**
 * @Author John
 * @Date 2020/8/29下午8:09
 */
@Repository
public interface HrMapper extends BaseMapper<HrDO> {

    HrDO loadUserByUsername(String username);

}
