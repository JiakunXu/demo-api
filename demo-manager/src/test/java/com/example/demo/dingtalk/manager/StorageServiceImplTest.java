package com.example.demo.dingtalk.manager;

import com.aliyun.dingtalkstorage_1_0.Client;
import com.aliyun.dingtalkstorage_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.demo.dingtalk.api.bo.FileInfo;
import com.example.demo.dingtalk.api.bo.FileUploadInfo;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StorageServiceImplTest {
    @Test
    void getFileUploadInfo0BuildsRequestAndMapsResponse() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        GetFileUploadInfoResponse response = JSON.parseObject("{\"body\": {\"uploadKey\": \"upload-key\"}}", GetFileUploadInfoResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getFileUploadInfoWithOptions(eq(spaceId), any(GetFileUploadInfoRequest.class), any(GetFileUploadInfoHeaders.class), any(RuntimeOptions.class))).thenReturn(response))) {
            assertThat(target.getFileUploadInfo(accessToken, spaceId, unionId)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"uploadKey\":\"upload-key\"}", FileUploadInfo.class));
            ArgumentCaptor<GetFileUploadInfoRequest> captor = ArgumentCaptor.forClass(GetFileUploadInfoRequest.class);
            ArgumentCaptor<GetFileUploadInfoHeaders> headers = ArgumentCaptor.forClass(GetFileUploadInfoHeaders.class);
            verify(clients.constructed().getFirst()).getFileUploadInfoWithOptions(eq(spaceId), captor.capture(), headers.capture(), any(RuntimeOptions.class));
            GetFileUploadInfoRequest request = captor.getValue();
            assertThat(request.getUnionId()).isEqualTo(unionId);
            assertThat(request.getMultipart()).isEqualTo(false);
            assertThat(headers.getValue().getXAcsDingtalkAccessToken()).isEqualTo(accessToken);
        }
    }

    @Test
    void getFileUploadInfo0PreservesTransportFailure() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getFileUploadInfoWithOptions(eq(spaceId), any(GetFileUploadInfoRequest.class), any(GetFileUploadInfoHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getFileUploadInfo(accessToken, spaceId, unionId)).hasCause(failure);
        }
    }

    @Test
    void getFileUploadInfo0UsesProviderErrorMessage() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.getFileUploadInfoWithOptions(eq(spaceId), any(GetFileUploadInfoRequest.class), any(GetFileUploadInfoHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.getFileUploadInfo(accessToken, spaceId, unionId)).hasMessage("provider rejected");
        }
    }

    @Test
    void commitFile1BuildsRequestAndMapsResponse() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        String uploadKey = "uploadKey-value";
        String name = "name-value";
        String parentId = "parentId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        CommitFileResponse response = JSON.parseObject("{\"body\": {\"dentry\": {\"id\": \"file-1\", \"name\": \"document\", \"spaceId\": \"space-1\"}}}", CommitFileResponse.class);
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.commitFileWithOptions(eq(spaceId), any(CommitFileRequest.class), any(CommitFileHeaders.class), any(RuntimeOptions.class))).thenReturn(response))) {
            assertThat(target.commitFile(accessToken, spaceId, unionId, uploadKey, name, parentId)).usingRecursiveComparison().isEqualTo(JSON.parseObject("{\"id\":\"file-1\",\"name\":\"document\",\"spaceId\":\"space-1\"}", FileInfo.class));
            ArgumentCaptor<CommitFileRequest> captor = ArgumentCaptor.forClass(CommitFileRequest.class);
            ArgumentCaptor<CommitFileHeaders> headers = ArgumentCaptor.forClass(CommitFileHeaders.class);
            verify(clients.constructed().getFirst()).commitFileWithOptions(eq(spaceId), captor.capture(), headers.capture(), any(RuntimeOptions.class));
            CommitFileRequest request = captor.getValue();
            assertThat(request.getUnionId()).isEqualTo(unionId);
            assertThat(request.getUploadKey()).isEqualTo(uploadKey);
            assertThat(request.getName()).isEqualTo(name);
            assertThat(request.getParentId()).isEqualTo(parentId);
            assertThat(headers.getValue().getXAcsDingtalkAccessToken()).isEqualTo(accessToken);
        }
    }

    @Test
    void commitFile1PreservesTransportFailure() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        String uploadKey = "uploadKey-value";
        String name = "name-value";
        String parentId = "parentId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        Exception failure = new Exception("transport unavailable");
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.commitFileWithOptions(eq(spaceId), any(CommitFileRequest.class), any(CommitFileHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.commitFile(accessToken, spaceId, unionId, uploadKey, name, parentId)).hasCause(failure);
        }
    }

    @Test
    void commitFile1UsesProviderErrorMessage() throws Exception {
        String accessToken = "accessToken-value";
        String spaceId = "spaceId-value";
        String unionId = "unionId-value";
        String uploadKey = "uploadKey-value";
        String name = "name-value";
        String parentId = "parentId-value";
        StorageServiceImpl target = new StorageServiceImpl();
        TeaException failure = new TeaException(java.util.Map.of("code", "Denied", "message", "provider rejected"));
        try (var clients = mockConstruction(Client.class, (client, context) ->
                when(client.commitFileWithOptions(eq(spaceId), any(CommitFileRequest.class), any(CommitFileHeaders.class), any(RuntimeOptions.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> target.commitFile(accessToken, spaceId, unionId, uploadKey, name, parentId)).hasMessage("provider rejected");
        }
    }
}
