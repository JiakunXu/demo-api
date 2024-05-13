package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dingtalkworkflow_1_0.Client;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.demo.dingtalk.api.WorkflowService;
import com.example.demo.dingtalk.api.bo.FormValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowServiceImpl implements WorkflowService {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    @Override
    public Long getAttachmentSpace(String accessToken, String userId) throws RuntimeException {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        GetAttachmentSpaceHeaders headers = new GetAttachmentSpaceHeaders()
            .setXAcsDingtalkAccessToken(accessToken);

        GetAttachmentSpaceRequest request = new GetAttachmentSpaceRequest().setUserId(userId);

        GetAttachmentSpaceResponse response;

        try {
            response = client.getAttachmentSpaceWithOptions(request, headers, new RuntimeOptions());
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return response.getBody().getResult().getSpaceId();
    }

    @Override
    public String startProcessInstance(String accessToken, String originatorUserId,
                                       String processCode, Long deptId,
                                       List<FormValue> formValueList) throws RuntimeException {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        StartProcessInstanceHeaders headers = new StartProcessInstanceHeaders()
            .setXAcsDingtalkAccessToken(accessToken);

        List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> formComponentValues = new ArrayList<>();

        if (formValueList != null && !formValueList.isEmpty()) {
            for (FormValue formValue : formValueList) {
                if (StringUtils.isBlank(formValue.getValue())) {
                    continue;
                }

                formComponentValues.add(
                    new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues()
                        .setName(formValue.getName()).setValue(formValue.getValue())
                        .setExtValue(formValue.getExtValue()));
            }
        }

        StartProcessInstanceRequest request = new StartProcessInstanceRequest()
            .setOriginatorUserId(originatorUserId).setProcessCode(processCode).setDeptId(deptId)
            .setFormComponentValues(formComponentValues);

        StartProcessInstanceResponse response;

        try {
            response = client.startProcessInstanceWithOptions(request, headers,
                new RuntimeOptions());
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return response.getBody().getInstanceId();
    }

    @Override
    public Boolean terminateProcessInstance(String accessToken, String processInstanceId,
                                            String operatingUserId) throws RuntimeException {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        TerminateProcessInstanceHeaders headers = new TerminateProcessInstanceHeaders()
            .setXAcsDingtalkAccessToken(accessToken);

        TerminateProcessInstanceRequest request = new TerminateProcessInstanceRequest()
            .setProcessInstanceId(processInstanceId).setOperatingUserId(operatingUserId);

        TerminateProcessInstanceResponse response;

        try {
            response = client.terminateProcessInstanceWithOptions(request, headers,
                new RuntimeOptions());
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        return response.getBody().getSuccess();
    }

}
