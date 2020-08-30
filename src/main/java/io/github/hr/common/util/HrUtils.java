package io.github.hr.common.util;

import io.github.hr.model.HrDO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author John
 * @Date 2020/8/29下午11:02
 */
public class HrUtils {

    public static HrDO getCurrentHr() {
        return (HrDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
