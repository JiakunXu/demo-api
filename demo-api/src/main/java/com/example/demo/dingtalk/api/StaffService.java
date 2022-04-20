package com.example.demo.dingtalk.api;

import java.util.List;

import com.example.demo.dingtalk.api.bo.Staff;

/**
 * @author JiakunXu
 */
public interface StaffService {

    String HTTPS_LIST_URL = "https://oapi.dingtalk.com/topapi/v2/user/list";

    String HTTPS_GET_URL  = "https://oapi.dingtalk.com/user/get";

    /**
     * 
     * @param accessToken
     * @param deptId
     * @return
     */
    List<Staff> listStaffs(String accessToken, Long deptId);

    /**
     * 
     * @param accessToken
     * @param staffId
     * @return
     * @throws RuntimeException
     */
    Staff getStaff(String accessToken, String staffId) throws RuntimeException;

    /**
     * 
     * @param accessToken
     * @param staffId
     * @param lang
     * @return
     * @throws RuntimeException
     */
    Staff getStaff(String accessToken, String staffId, String lang) throws RuntimeException;

}
