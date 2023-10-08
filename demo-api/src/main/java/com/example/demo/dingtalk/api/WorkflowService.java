package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.FormValue;

import java.util.List;

public interface WorkflowService {

    Long getAttachmentSpace(String accessToken, String userId) throws RuntimeException;

    String startProcessInstance(String accessToken, String originatorUserId, String processCode,
                                Long deptId, List<FormValue> formValueList) throws RuntimeException;

    Boolean terminateProcessInstance(String accessToken, String processInstanceId,
                                     String operatingUserId) throws RuntimeException;

}
