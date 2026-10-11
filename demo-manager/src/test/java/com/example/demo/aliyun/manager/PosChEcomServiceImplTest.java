package com.example.demo.aliyun.manager;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.alinlp.model.v20200629.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PosChEcomServiceImplTest {
    private PosChEcomServiceImpl service() {
        var service = new PosChEcomServiceImpl();
        ReflectionTestUtils.setField(service, "regionId", "region");
        ReflectionTestUtils.setField(service, "accessKeyId", "access");
        ReflectionTestUtils.setField(service, "secret", "secret");
        return service;
    }

    @Test
    void sendsNlpRequestAndReturnsData() throws Exception {
        var profile = mock(DefaultProfile.class);
        var response = new GetPosChEcomResponse();
        response.setData("tagged words");
        try (MockedStatic<DefaultProfile> profiles = mockStatic(DefaultProfile.class);
             MockedConstruction<DefaultAcsClient> clients = mockConstruction(DefaultAcsClient.class, (client, context) -> {
                 assertThat(context.arguments().toArray()).containsExactly(profile);
                 when(client.getAcsResponse(any(GetPosChEcomRequest.class))).thenReturn(response);
             })) {
            profiles.when(() -> DefaultProfile.getProfile("region", "access", "secret")).thenReturn(profile);
            assertThat(service().getPosChEcom("商品文本")).isEqualTo("tagged words");
            var request = ArgumentCaptor.forClass(GetPosChEcomRequest.class);
            verify(clients.constructed().getFirst()).getAcsResponse(request.capture());
            assertThat(request.getValue().getText()).isEqualTo("商品文本");
            assertThat(request.getValue().getServiceCode()).isEqualTo("alinlp");
            assertThat(request.getValue().getTokenizerId()).isEqualTo("GENERAL_CHN");
            assertThat(request.getValue().getSysProductDomain().getDomainName()).isEqualTo("alinlp.cn-hangzhou.aliyuncs.com");
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void preservesServerAndClientFailures(boolean server) throws Exception {
        ClientException failure = server ? new ServerException("denied", "rejected", "request") : new ClientException("network", "offline");
        try (MockedStatic<DefaultProfile> profiles = mockStatic(DefaultProfile.class);
             MockedConstruction<DefaultAcsClient> clients = mockConstruction(DefaultAcsClient.class, (client, context) -> when(client.getAcsResponse(any(GetPosChEcomRequest.class))).thenThrow(failure))) {
            profiles.when(() -> DefaultProfile.getProfile("region", "access", "secret")).thenReturn(mock(DefaultProfile.class));
            assertThatThrownBy(() -> service().getPosChEcom("text")).hasCause(failure);
        }
    }
}
