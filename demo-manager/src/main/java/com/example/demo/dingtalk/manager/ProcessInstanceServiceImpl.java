package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.example.demo.dingtalk.api.ProcessInstanceService;
import com.example.demo.dingtalk.api.bo.proc.FormValue;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceServiceImpl.class);

    @Override
    public String create(String accessToken, Long agentId, String processCode,
                         String originatorUserId, Long deptId, String approvers, String ccList,
                         String ccPosition, List<FormValue> formList,
                         List<List<FormValue>> itemList) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (agentId == null) {
            throw new RuntimeException("agent_id is null.");
        }

        if (StringUtils.isBlank(processCode)) {
            throw new RuntimeException("process_code is null.");
        }

        if (StringUtils.isBlank(originatorUserId)) {
            throw new RuntimeException("originator_user_id is null.");
        }

        if (deptId == null) {
            throw new RuntimeException("dept_id is null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(ProcessInstanceService.HTTPS_CREATE_URL);

        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setAgentId(agentId);
        request.setProcessCode(processCode);
        request.setOriginatorUserId(originatorUserId);
        request.setDeptId(deptId);
        request.setApprovers(approvers);
        request.setCcList(ccList);
        request.setCcPosition(ccPosition);

        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVoList = new ArrayList<>();

        if (formList != null && formList.size() > 0) {
            for (FormValue formValue : formList) {
                OapiProcessinstanceCreateRequest.FormComponentValueVo formComponentValueVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
                formComponentValueVo.setName(formValue.getName());
                formComponentValueVo.setValue(formValue.getValue());
                formComponentValueVo.setExtValue(formValue.getExtValue());
                formComponentValueVoList.add(formComponentValueVo);
            }
        }

        if (itemList != null && itemList.size() > 0) {
            List<List<OapiProcessinstanceCreateRequest.FormComponentValueVo>> itemComponentValueVoList = new ArrayList<>();

            for (List<FormValue> formValueList : itemList) {
                List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValueVos = new ArrayList<>();

                for (FormValue formValue : formValueList) {
                    OapiProcessinstanceCreateRequest.FormComponentValueVo formComponentValueVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
                    formComponentValueVo.setName(formValue.getName());
                    formComponentValueVo.setValue(formValue.getValue());
                    formComponentValueVo.setExtValue(formValue.getValue());
                    formComponentValueVos.add(formComponentValueVo);
                }

                itemComponentValueVoList.add(formComponentValueVos);
            }

            OapiProcessinstanceCreateRequest.FormComponentValueVo formComponentValueVo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            formComponentValueVo.setName("明细");
            formComponentValueVo.setValue(JSON.toJSONString(itemComponentValueVoList));
            formComponentValueVoList.add(formComponentValueVo);
        }

        request.setFormComponentValues(formComponentValueVoList);

        OapiProcessinstanceCreateResponse response = null;

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

        return response.getProcessInstanceId();
    }

}
