package com.example.demo.menu.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@ToString
public class MenuDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 6834457453642427426L;

    private BigInteger        id;

    /**
     * 上级
     */
    private BigInteger        pid;

    /**
     * 目录/菜单/按钮
     */
    private String            type;

    /**
     * icon
     */
    private String            icon;

    /**
     * 名称
     */
    private String            name;

    /**
     * 路由地址
     */
    private String            path;

    /**
     * 组件路径
     */
    private String            component;

    /**
     * 路由参数
     */
    private String            query;

    /**
     * 权限
     */
    private String            code;

    /**
     * 排序
     */
    private Integer           order;

    /**
     * 0 不是 1 是
     */
    private Boolean           external;

    /**
     * 0 不缓存 1 缓存
     */
    private Boolean           cached;

    /**
     * 0 显示 1 不显示
     */
    private Boolean           hidden;

    /**
     * 正常 停用
     */
    private String            status;

    private List<BigInteger>  ids;

    private String[]          types;

    /**
     * user user_role role_menu menu.
     */
    private BigInteger        userId;

}
