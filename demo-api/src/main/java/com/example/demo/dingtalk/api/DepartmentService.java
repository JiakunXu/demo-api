package com.example.demo.dingtalk.api;

import java.util.List;

import com.example.demo.dingtalk.api.bo.dept.Department;

/**
 * @author JiakunXu
 */
public interface DepartmentService {

    String HTTPS_LIST_URL = "https://oapi.dingtalk.com/department/list";

    String HTTPS_GET_URL  = "https://oapi.dingtalk.com/topapi/v2/department/get";

    /**
     * @param accessToken 调用接口凭证
     * @param lang        通讯录语言（默认zh_CN，未来会支持en_US）
     * @param fetchChild  是否递归部门的全部子部门
     * @param id          父部门id（如果不传，默认部门为根部门，根部门ID为1）
     * @return
     */
    List<Department> listDepartments(String accessToken, String lang, Boolean fetchChild,
                                     String id);

    /**
     * 
     * @param accessToken 调用企业接口凭证
     * @param deptId 部门ID，根部门ID为1
     * @param language
     * @return
     */
    Department getDepartment(String accessToken, Long deptId, String language);

}
