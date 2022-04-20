package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Department extends BaseResult {

    private static final long serialVersionUID = 688300481906550480L;

    private Long              id;

    private Long              parentId;

    private String            name;

    /**
     * 部门群ID.
     */
    private String            deptGroupChatId;

}
