package com.example.demo.dingtalk.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dingtalk.api.StaffService;
import com.example.demo.dingtalk.api.bo.user.Staff;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.taobao.api.ApiException;

/**
 * @author JiakunXu
 */
@Service
public class StaffServiceImpl implements StaffService {

    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);

    @Override
    public List<Staff> listStaffs(String accessToken, Long deptId) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (deptId == null) {
            throw new RuntimeException("dept_id cannot be null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(StaffService.HTTPS_LIST_URL);

        OapiV2UserListRequest request = new OapiV2UserListRequest();
        request.setDeptId(deptId);
        request.setCursor(0L);
        request.setSize(100L);
        request.setOrderField("custom");
        request.setContainAccessLimit(false);
        request.setLanguage("zh_CN");

        OapiV2UserListResponse response = null;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException("execute", e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        String errCode = response.getErrorCode();

        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(response.getErrmsg());
        }

        List<OapiV2UserListResponse.ListUserResponse> listUserResponseList = response.getResult()
            .getList();

        if (listUserResponseList == null || listUserResponseList.size() == 0) {
            return null;
        }

        List<Staff> list = new ArrayList<>();

        for (OapiV2UserListResponse.ListUserResponse listUserResponse : listUserResponseList) {
            Staff staff = new Staff();
            staff.setStaffId(listUserResponse.getUserid());
            staff.setUnionId(listUserResponse.getUnionid());
            staff.setName(listUserResponse.getName());
            staff.setMobile(listUserResponse.getMobile());
            staff.setDepartment(listUserResponse.getDeptIdList());
            staff.setPosition(listUserResponse.getTitle());
            staff.setAvatar(listUserResponse.getAvatar());

            list.add(staff);
        }

        return list;
    }

    @Override
    public Staff getStaff(String accessToken, String staffId) throws RuntimeException {
        return getStaff(accessToken, staffId, "zh_CN");
    }

    @Override
    public Staff getStaff(String accessToken, String staffId, String lang) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(staffId)) {
            throw new RuntimeException("userid cannot be null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(StaffService.HTTPS_GET_URL);

        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(staffId);
        request.setHttpMethod("GET");

        OapiUserGetResponse response = null;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException("execute", e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        String errCode = response.getErrorCode();

        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(response.getErrmsg());
        }

        Staff staff = new Staff();
        staff.setStaffId(response.getUserid());
        staff.setUnionId(response.getUnionid());
        staff.setName(response.getName());
        staff.setMobile(response.getMobile());
        staff.setDepartment(response.getDepartment());
        staff.setPosition(response.getPosition());
        staff.setAvatar(response.getAvatar());

        return staff;
    }

}
