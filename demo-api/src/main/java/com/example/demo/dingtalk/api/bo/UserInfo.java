package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2548641282413540943L;

    /**
     * 员工的userId
     */
    private String            userid;

    /**
     * 员工在当前开发者企业账号范围内的唯一标识
     */
    private String            unionid;

    /**
     * 所属部门id列表
     */
    private List<Long>        deptIdList;

}
