package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Staff extends BaseResult {

    private static final long serialVersionUID = -4673368456299605352L;

    /**
     * 员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改.
     */
    private String            staffId;

    /**
     * 员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变.
     */
    private String            unionId;

    /**
     * 员工名字.
     */
    private String            name;

    /**
     * 手机号码.
     */
    private String            mobile;

    /**
     * 成员所属部门id列表.
     */
    private List<Long>        department;

    /**
     * 职位信息.
     */
    private String            position;

    /**
     * 头像url.
     */
    private String            avatar;

}
