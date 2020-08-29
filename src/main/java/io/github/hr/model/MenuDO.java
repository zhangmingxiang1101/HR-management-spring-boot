package io.github.hr.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author John
 * @Date 2020/8/29下午8:40
 */
@Data
@TableName("menu")
public class MenuDO implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    /**
     * 封装状态
     */
    private Meta meta;
    private Integer parentId;
    private Boolean enabled;

    private List<MenuDO> children;
    private List<RoleDO> roles;

}
