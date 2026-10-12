package com.example.demo.dingtalk.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.dingtalk.api.*;
import com.example.demo.dingtalk.api.bo.*;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttachmentServiceImplTest {
    private final OssService oss = mock(OssService.class);
    private final StorageService storage = mock(StorageService.class);
    private final WorkflowService workflow = mock(WorkflowService.class);
    private final AttachmentServiceImpl service = new AttachmentServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "ossService", oss);
    ReflectionTestUtils.setField(service, "storageService", storage);
    ReflectionTestUtils.setField(service, "workflowService", workflow);
    }

    @Test
    void uploadAndCommitUseWorkflowSpaceAndReturnAttachmentMetadata() {
        when(workflow.getAttachmentSpace("token", "user")).thenReturn(42L);
        FileUploadInfo upload = new FileUploadInfo(); upload.setUploadKey("upload");
        FileUploadInfo.HeaderSignatureInfo signature = new FileUploadInfo.HeaderSignatureInfo(); signature.setHeaders(Map.of("Authorization", "signature")); signature.setResourceUrls(List.of("https://upload.example.com")); upload.setHeaderSignatureInfo(signature);
        when(storage.getFileUploadInfo("token", "42", "union")).thenReturn(upload);
        FileInfo committed = new FileInfo();
        committed.setSpaceId("42");
        committed.setId("file-id");
        committed.setName("doc.pdf");
        committed.setSize(123L);
        committed.setType("FILE");
        when(storage.commitFile("token", "42", "union", "upload", "doc.pdf", "0")).thenReturn(committed);
        var result = JSON.parseArray(service.getAttachment("token", "user", "union", "[{\"name\":\"doc.pdf\",\"url\":\"https://files.example.com/doc.pdf\"}]")).getJSONObject(0);
        assertThat(result.getString("fileId")).isEqualTo("file-id");
        assertThat(result.getLongValue("fileSize")).isEqualTo(123L);
        assertThat(result.getString("spaceId")).isEqualTo("42");
        var order = inOrder(oss, storage);
        order.verify(storage).getFileUploadInfo("token", "42", "union");
        order.verify(oss).putObject("https://upload.example.com", signature.getHeaders(), "https://files.example.com/doc.pdf");
        order.verify(storage).commitFile("token", "42", "union", "upload", "doc.pdf", "0");
    }

    @Test
    void blankAttachmentListAvoidsExternalCalls() { assertThat(service.getAttachment("token", "user", "union", " ")).isNull();
    verifyNoInteractions(oss, storage, workflow);
    }

    @Test
    void workflowFailurePreventsUpload() {
        when(workflow.getAttachmentSpace(anyString(), anyString())).thenThrow(new RuntimeException("workflow failed"));
        assertThatThrownBy(() -> service.getAttachment("token", "user", "union", "[]")).hasMessage("workflow failed");
        verifyNoInteractions(oss, storage);
    }
}
