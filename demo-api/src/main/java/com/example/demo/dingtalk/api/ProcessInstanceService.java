package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.process.FormValue;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface ProcessInstanceService {

    String HTTPS_CREATE_URL = "https://oapi.dingtalk.com/topapi/processinstance/create";

    /**
     *
     * @param accessToken
     * @param agentId
     * @param processCode 审批流的唯一码
     * @param originatorUserId 审批实例发起人的userid
     * @param deptId 发起人所在的部门，如果发起人属于根部门，传-1
     * @param approvers 审批人userid列表，最大列表长度20。
     *                  多个审批人用逗号分隔，按传入的顺序依次审批
     * @param ccList 抄送人userid列表，最大列表长度20
     * @param ccPosition 在什么节点抄送给抄送人：START/FINISH/START_FINISH
     * @param formList
     * @param itemList
     * @return
     */
    String create(String accessToken, Long agentId, String processCode, String originatorUserId,
                  Long deptId, String approvers, String ccList, String ccPosition,
                  List<FormValue> formList, List<List<FormValue>> itemList);

}
