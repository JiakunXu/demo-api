package com.example.demo.aliyun.manager;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.comm.SignVersion;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Date;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OssServiceImplTest {
    private OssServiceImpl service() {
        var service = new OssServiceImpl();
        ReflectionTestUtils.setField(service, "endpoint", "https://oss.example.com");
        ReflectionTestUtils.setField(service, "accessKeyId", "access");
        ReflectionTestUtils.setField(service, "secretAccessKey", "secret");
        return service;
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void uploadsBytesOrStreamAndShutsDown(boolean bytes) throws Exception {
        OSS oss = mock(OSS.class);
        try (MockedConstruction<OSSClientBuilder> builders = mockConstruction(OSSClientBuilder.class, (builder, context) -> when(builder.build("https://oss.example.com", "access", "secret")).thenReturn(oss))) {
            byte[] content = {1, 2, 3};
            String result = bytes ? service().putObject("bucket", "key", content, "image/png") : service().putObject("bucket", "key", new ByteArrayInputStream(content), "image/png");
            assertThat(result).isEqualTo("key");
            var request = ArgumentCaptor.forClass(PutObjectRequest.class);
            verify(oss).putObject(request.capture());
            assertThat(request.getValue().getBucketName()).isEqualTo("bucket");
            assertThat(request.getValue().getKey()).isEqualTo("key");
            assertThat(request.getValue().getInputStream().readAllBytes()).containsExactly(content);
            assertThat(request.getValue().getMetadata().getContentType()).isEqualTo("image/png");
            verify(oss).shutdown();
        }
    }

    @Test
    void copiesObjectAndOverridesContentType() {
        OSS oss = mock(OSS.class);
        try (MockedConstruction<OSSClientBuilder> builders = mockConstruction(OSSClientBuilder.class, (builder, context) -> when(builder.build(anyString(), anyString(), anyString())).thenReturn(oss))) {
            assertThat(service().copyObject("source", "old", "destination", "new", "image/png")).isEqualTo("new");
            var request = ArgumentCaptor.forClass(CopyObjectRequest.class);
            verify(oss).copyObject(request.capture());
            assertThat(request.getValue().getSourceBucketName()).isEqualTo("source");
            assertThat(request.getValue().getSourceKey()).isEqualTo("old");
            assertThat(request.getValue().getDestinationBucketName()).isEqualTo("destination");
            assertThat(request.getValue().getDestinationKey()).isEqualTo("new");
            assertThat(request.getValue().getNewObjectMetadata().getContentType()).isEqualTo("image/png");
            verify(oss).shutdown();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void uploadAndCopyFailuresPreserveCauseAndCloseClient(boolean server) {
        for (boolean copy : new boolean[] {true, false}) {
            OSS oss = mock(OSS.class);
            RuntimeException failure = server ? new OSSException("rejected") : new ClientException("network");
            when(oss.putObject(any(PutObjectRequest.class))).thenThrow(failure);
            when(oss.copyObject(any(CopyObjectRequest.class))).thenThrow(failure);
            try (MockedConstruction<OSSClientBuilder> builders = mockConstruction(OSSClientBuilder.class, (builder, context) -> when(builder.build(anyString(), anyString(), anyString())).thenReturn(oss))) {
                assertThatThrownBy(() -> {
                    if (copy) service().copyObject("source", "old", "destination", "new", "image/png");
                    else service().putObject("bucket", "key", new byte[0], "image/png");
                }).hasCause(failure);
                verify(oss).shutdown();
            }
        }
    }

    @Test
    void presignsPutWithTenMinuteExpirationAndV4() throws Exception {
        OSS oss = mock(OSS.class);
        var builder = mock(OSSClientBuilder.OSSClientBuilderImpl.class, RETURNS_SELF);
        when(builder.build()).thenReturn(oss);
        var url = URI.create("https://oss.example.com/signed").toURL();
        when(oss.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url);
        long before = System.currentTimeMillis();
        try (MockedStatic<OSSClientBuilder> factory = mockStatic(OSSClientBuilder.class)) {
            factory.when(OSSClientBuilder::create).thenReturn(builder);
            assertThat(service().generatePresignedUrl("bucket", "key", "image/png")).isEqualTo(url);
            var request = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);
            verify(oss).generatePresignedUrl(request.capture());
            assertThat(request.getValue().getMethod()).isEqualTo(HttpMethod.PUT);
            assertThat(request.getValue().getBucketName()).isEqualTo("bucket");
            assertThat(request.getValue().getKey()).isEqualTo("key");
            assertThat(request.getValue().getHeaders()).containsEntry("Content-Type", "image/png");
            assertThat(request.getValue().getExpiration().getTime()).isBetween(before + 599000, System.currentTimeMillis() + 601000);
            var config = ArgumentCaptor.forClass(ClientConfiguration.class);
            verify(builder).clientConfiguration(config.capture());
            assertThat(config.getValue().getProtocol()).isEqualTo(Protocol.HTTPS);
            assertThat(config.getValue().getSignatureVersion()).isEqualTo(SignVersion.V4);
            verify(builder).endpoint("https://oss.example.com");
            verify(builder).region("cn-hangzhou");
            verify(oss).shutdown();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void presignsGetWithOptionalProcessing(boolean process) throws Exception {
        OSS oss = mock(OSS.class);
        var builder = mock(OSSClientBuilder.OSSClientBuilderImpl.class, RETURNS_SELF);
        when(builder.build()).thenReturn(oss);
        var url = URI.create("https://oss.example.com/signed").toURL();
        when(oss.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(url);
        Date expiration = new Date(2000000000000L);
        try (MockedStatic<OSSClientBuilder> factory = mockStatic(OSSClientBuilder.class)) {
            factory.when(OSSClientBuilder::create).thenReturn(builder);
            assertThat(process ? service().generatePresignedUrl("bucket", "key", expiration, "image/resize,w_100") : service().generatePresignedUrl("bucket", "key", expiration)).isEqualTo(url);
            var request = ArgumentCaptor.forClass(GeneratePresignedUrlRequest.class);
            verify(oss).generatePresignedUrl(request.capture());
            assertThat(request.getValue().getMethod()).isEqualTo(HttpMethod.GET);
            assertThat(request.getValue().getExpiration()).isEqualTo(expiration);
            assertThat(request.getValue().getProcess()).isEqualTo(process ? "image/resize,w_100" : null);
            verify(oss).shutdown();
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void presignFailuresCloseClient(boolean server) {
        for (boolean put : new boolean[] {true, false}) {
            OSS oss = mock(OSS.class);
            var builder = mock(OSSClientBuilder.OSSClientBuilderImpl.class, RETURNS_SELF);
            when(builder.build()).thenReturn(oss);
            RuntimeException failure = server ? new OSSException("rejected") : new ClientException("network");
            when(oss.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenThrow(failure);
            try (MockedStatic<OSSClientBuilder> factory = mockStatic(OSSClientBuilder.class)) {
                factory.when(OSSClientBuilder::create).thenReturn(builder);
                assertThatThrownBy(() -> {
                    if (put) service().generatePresignedUrl("bucket", "key", "image/png");
                    else service().generatePresignedUrl("bucket", "key", new Date());
                }).hasCause(failure);
                verify(oss).shutdown();
            }
        }
    }
}
