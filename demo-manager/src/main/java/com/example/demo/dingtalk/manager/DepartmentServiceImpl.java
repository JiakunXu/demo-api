package com.example.demo.dingtalk.manager;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dingtalk.api.DepartmentService;
import com.example.demo.dingtalk.api.bo.Department;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.taobao.api.ApiException;

/**
 * @author JiakunXu
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public List<Department> listDepartments(String accessToken, String lang, Boolean fetchChild,
                                            String id) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(DepartmentService.HTTPS_LIST_URL);

        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setLang(lang);
        request.setFetchChild(fetchChild);
        request.setId(id);
        request.setHttpMethod("GET");

        OapiDepartmentListResponse response = null;

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

        List<OapiDepartmentListResponse.Department> department = response.getDepartment();

        if (department == null || department.size() == 0) {
            return null;
        }

        List<Department> list = new ArrayList<>();

        for (OapiDepartmentListResponse.Department d : department) {
            Department dept = new Department();
            dept.setId(d.getId());
            dept.setParentId(d.getParentid());
            dept.setName(d.getName());

            list.add(dept);
        }

        return list;
    }

    @Override
    public Department getDepartment(String accessToken, Long deptId, String language) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(DepartmentService.HTTPS_GET_URL);

        OapiV2DepartmentGetRequest request = new OapiV2DepartmentGetRequest();
        request.setDeptId(deptId);
        request.setLanguage(language);

        OapiV2DepartmentGetResponse response = null;

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

        Department department = new Department();
        department.setDeptGroupChatId(response.getResult().getDeptGroupChatId());

        return department;
    }

}
