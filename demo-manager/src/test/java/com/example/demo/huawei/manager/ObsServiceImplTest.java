package com.example.demo.huawei.manager;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import org.mockito.MockedConstruction;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObsServiceImplTest {
    private ObsServiceImpl service() {
        var service = new ObsServiceImpl();
        ReflectionTestUtils.setField(service, "accessKey", "access");
        ReflectionTestUtils.setField(service, "secretKey", "secret");
        ReflectionTestUtils.setField(service, "endPoint", "https://obs.example.com");
        return service;
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void uploadsBothPayloadTypesAndClosesClient(boolean bytes) throws Exception {
        try (MockedConstruction<ObsClient> clients = mockConstruction(ObsClient.class, (client, context) -> assertThat(context.arguments().toArray()).containsExactly("access", "secret", "https://obs.example.com"))) {
            byte[] content = {1, 2, 3};
            String result = bytes ? service().putObject("bucket", "key", content, "image/png") : service().putObject("bucket", "key", new ByteArrayInputStream(content), "image/png");
            assertThat(result).isEqualTo("key");
            var client = clients.constructed().getFirst();
            var request = ArgumentCaptor.forClass(PutObjectRequest.class);
            verify(client).putObject(request.capture());
            assertThat(request.getValue().getBucketName()).isEqualTo("bucket");
            assertThat(request.getValue().getObjectKey()).isEqualTo("key");
            assertThat(request.getValue().getInput().readAllBytes()).containsExactly(content);
            assertThat(request.getValue().getMetadata().getContentType()).isEqualTo("image/png");
            verify(client).close();
        }
    }

    @Test
    void uploadFailureStillClosesClient() throws Exception {
        var failure = new ObsException("rejected");
        try (MockedConstruction<ObsClient> clients = mockConstruction(ObsClient.class, (client, context) -> when(client.putObject(any(PutObjectRequest.class))).thenThrow(failure))) {
            assertThatThrownBy(() -> service().putObject("bucket", "key", new byte[0], "image/png")).hasCause(failure);
            verify(clients.constructed().getFirst()).close();
        }
    }

    @Test
    void closeFailureDoesNotDiscardSuccessfulUpload() throws Exception {
        try (MockedConstruction<ObsClient> clients = mockConstruction(ObsClient.class, (client, context) -> doThrow(new IOException("close failed")).when(client).close())) {
            assertThat(service().putObject("bucket", "key", new byte[0], "image/png")).isEqualTo("key");
            verify(clients.constructed().getFirst()).close();
        }
    }
}
