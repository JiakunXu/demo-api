package com.example.demo.dingtalk.manager;

import com.aliyun.dingtalkworkflow_1_0.Client;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.demo.dingtalk.api.bo.FormValue;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WorkflowServiceImplTest {
    @Test
    void getAttachmentSpace0BuildsRequestAndMapsResponse() throws Exception {
        String accessToken = "accessToken-value";
        String userId = "userId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        GetAttachmentSpaceResponse response = JSON.parseObject("{\"body\": {\"result\": {\"spaceId\": 42}}}", GetAttachmentSpaceResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAttachmentSpaceWithOptions(any(GetAttachmentSpaceRequest.class), any(GetAttachmentSpaceHeaders.class), any(RuntimeOptions.class))).thenReturn(response))) {
            assertThat(target.getAttachmentSpace(accessToken, userId)).isEqualTo(42L);
            ArgumentCaptor<GetAttachmentSpaceRequest> captor = ArgumentCaptor.forClass(GetAttachmentSpaceRequest.class);
            ArgumentCaptor<GetAttachmentSpaceHeaders> headers = ArgumentCaptor.forClass(GetAttachmentSpaceHeaders.class);
            verify(clients.constructed().getFirst()).getAttachmentSpaceWithOptions(captor.capture(), headers.capture(), any(RuntimeOptions.class));
            GetAttachmentSpaceRequest request = captor.getValue();
            assertThat(request.getUserId()).isEqualTo(userId);
            assertThat(headers.getValue().getXAcsDingtalkAccessToken()).isEqualTo(accessToken);
        }
    }

    @Test
    void getAttachmentSpace0PreservesTransportFailure() throws Exception {
        String accessToken = "accessToken-value";
        String userId = "userId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAttachmentSpaceWithOptions(any(GetAttachmentSpaceRequest.class), any(GetAttachmentSpaceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getAttachmentSpace(accessToken, userId)).hasCause(failure);
        }
    }

    @Test
    void getAttachmentSpace0UsesProviderErrorMessage() throws Exception {
        String accessToken = "accessToken-value";
        String userId = "userId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getAttachmentSpaceWithOptions(any(GetAttachmentSpaceRequest.class), any(GetAttachmentSpaceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getAttachmentSpace(accessToken, userId)).hasMessage("provider rejected");
        }
    }

    @Test
    void startProcessInstance1BuildsRequestAndMapsResponse() throws Exception {
        String accessToken = "accessToken-value";
        String originatorUserId = "originatorUserId-value";
        String processCode = "processCode-value";
        Long deptId = 42L;
        List<FormValue> formValueList = java.util.List.of(new FormValue("审批事项", "值", "扩展"), new FormValue("空项", " "));
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        StartProcessInstanceResponse response = JSON.parseObject("{\"body\": {\"instanceId\": \"process-1\"}}", StartProcessInstanceResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.startProcessInstanceWithOptions(any(StartProcessInstanceRequest.class), any(StartProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenReturn(response))) {
            assertThat(target.startProcessInstance(accessToken, originatorUserId, processCode, deptId, formValueList)).isEqualTo("process-1");
            ArgumentCaptor<StartProcessInstanceRequest> captor = ArgumentCaptor.forClass(StartProcessInstanceRequest.class);
            ArgumentCaptor<StartProcessInstanceHeaders> headers = ArgumentCaptor.forClass(StartProcessInstanceHeaders.class);
            verify(clients.constructed().getFirst()).startProcessInstanceWithOptions(captor.capture(), headers.capture(), any(RuntimeOptions.class));
            StartProcessInstanceRequest request = captor.getValue();
            assertThat(request.getOriginatorUserId()).isEqualTo(originatorUserId);
            assertThat(request.getProcessCode()).isEqualTo(processCode);
            assertThat(request.getDeptId()).isEqualTo(deptId);
            assertThat(headers.getValue().getXAcsDingtalkAccessToken()).isEqualTo(accessToken);
            assertThat(request.getFormComponentValues()).hasSize(1);
            assertThat(request.getFormComponentValues().getFirst().getName()).isEqualTo("审批事项");
            assertThat(request.getFormComponentValues().getFirst().getExtValue()).isEqualTo("扩展");
        }
    }

    @Test
    void startProcessInstance1PreservesTransportFailure() throws Exception {
        String accessToken = "accessToken-value";
        String originatorUserId = "originatorUserId-value";
        String processCode = "processCode-value";
        Long deptId = 42L;
        List<FormValue> formValueList = java.util.List.of(new FormValue("审批事项", "值", "扩展"), new FormValue("空项", " "));
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.startProcessInstanceWithOptions(any(StartProcessInstanceRequest.class), any(StartProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.startProcessInstance(accessToken, originatorUserId, processCode, deptId, formValueList)).hasCause(failure);
        }
    }

    @Test
    void startProcessInstance1UsesProviderErrorMessage() throws Exception {
        String accessToken = "accessToken-value";
        String originatorUserId = "originatorUserId-value";
        String processCode = "processCode-value";
        Long deptId = 42L;
        List<FormValue> formValueList = java.util.List.of(new FormValue("审批事项", "值", "扩展"), new FormValue("空项", " "));
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.startProcessInstanceWithOptions(any(StartProcessInstanceRequest.class), any(StartProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.startProcessInstance(accessToken, originatorUserId, processCode, deptId, formValueList)).hasMessage("provider rejected");
        }
    }

    @Test
    void terminateProcessInstance2BuildsRequestAndMapsResponse() throws Exception {
        String accessToken = "accessToken-value";
        String processInstanceId = "processInstanceId-value";
        String operatingUserId = "operatingUserId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        TerminateProcessInstanceResponse response = JSON.parseObject("{\"body\": {\"success\": true}}", TerminateProcessInstanceResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.terminateProcessInstanceWithOptions(any(TerminateProcessInstanceRequest.class), any(TerminateProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenReturn(response))) {
            assertThat(target.terminateProcessInstance(accessToken, processInstanceId, operatingUserId)).isEqualTo(true);
            ArgumentCaptor<TerminateProcessInstanceRequest> captor = ArgumentCaptor.forClass(TerminateProcessInstanceRequest.class);
            ArgumentCaptor<TerminateProcessInstanceHeaders> headers = ArgumentCaptor.forClass(TerminateProcessInstanceHeaders.class);
            verify(clients.constructed().getFirst()).terminateProcessInstanceWithOptions(captor.capture(), headers.capture(), any(RuntimeOptions.class));
            TerminateProcessInstanceRequest request = captor.getValue();
            assertThat(request.getProcessInstanceId()).isEqualTo(processInstanceId);
            assertThat(request.getOperatingUserId()).isEqualTo(operatingUserId);
            assertThat(headers.getValue().getXAcsDingtalkAccessToken()).isEqualTo(accessToken);
        }
    }

    @Test
    void terminateProcessInstance2PreservesTransportFailure() throws Exception {
        String accessToken = "accessToken-value";
        String processInstanceId = "processInstanceId-value";
        String operatingUserId = "operatingUserId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.terminateProcessInstanceWithOptions(any(TerminateProcessInstanceRequest.class), any(TerminateProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.terminateProcessInstance(accessToken, processInstanceId, operatingUserId)).hasCause(failure);
        }
    }

    @Test
    void terminateProcessInstance2UsesProviderErrorMessage() throws Exception {
        String accessToken = "accessToken-value";
        String processInstanceId = "processInstanceId-value";
        String operatingUserId = "operatingUserId-value";
        WorkflowServiceImpl target = new WorkflowServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.terminateProcessInstanceWithOptions(any(TerminateProcessInstanceRequest.class), any(TerminateProcessInstanceHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.terminateProcessInstance(accessToken, processInstanceId, operatingUserId)).hasMessage("provider rejected");
        }
    }
}
