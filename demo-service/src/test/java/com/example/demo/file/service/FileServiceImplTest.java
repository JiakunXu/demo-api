package com.example.demo.file.service;

import com.example.demo.aliyun.api.OssService;
import org.mockito.ArgumentCaptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceImplTest {
    private final OssService oss = mock(OssService.class);
    private final FileServiceImpl service = new FileServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "ossService", oss); ReflectionTestUtils.setField(service, "bucketName", "bucket"); ReflectionTestUtils.setField(service, "url", "https://files.example.com"); }
    @Test
    void uploadGeneratesDatedUniqueKeyPreservingExtensionAndPublicUrl() {
        when(oss.putObject(anyString(), anyString(), any(InputStream.class), anyString())).thenAnswer(invocation -> invocation.getArgument(1));
        InputStream content = new ByteArrayInputStream(new byte[] {1, 2});
        var result = service.upload("image.png?token=secret", "image/png", content);
        var key = ArgumentCaptor.forClass(String.class);
        verify(oss).putObject(eq("bucket"), key.capture(), same(content), eq("image/png"));
        assertThat(key.getValue()).matches("[0-9]{4}/[0-9]{2}/[0-9]{2}/[0-9a-f-]{36}\\.png");
        assertThat(result.getUrl()).isEqualTo("https://files.example.com/" + key.getValue());
        assertThat(result.getName()).isEqualTo("image.png?token=secret");
        assertThat(result.getContentType()).isEqualTo("image/png");
    }

    @Test
    void missingNameTypeOrContentPreventsUpload() {
        assertThatThrownBy(() -> service.upload(" ", "image/png", new ByteArrayInputStream(new byte[0]))).hasMessage("参数信息不能为空");
        assertThatThrownBy(() -> service.upload("a.png", "", new ByteArrayInputStream(new byte[0]))).hasMessage("参数信息不能为空");
        assertThatThrownBy(() -> service.upload("a.png", "image/png", null)).hasMessage("参数信息不能为空");
        verifyNoInteractions(oss);
    }

    @Test
    void providerFailurePropagates() {
        when(oss.putObject(anyString(), anyString(), any(InputStream.class), anyString())).thenThrow(new RuntimeException("storage failed"));
        assertThatThrownBy(() -> service.upload("a.png", "image/png", new ByteArrayInputStream(new byte[0]))).hasMessage("storage failed");
    }
}
