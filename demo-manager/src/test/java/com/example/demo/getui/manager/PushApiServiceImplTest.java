package com.example.demo.getui.manager;

import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.ArgumentCaptor;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PushApiServiceImplTest {
    private static PushApi api;
    private static PushApiServiceImpl service;

    @BeforeAll
    static void initializeWithoutExternalService() {
        api = mock(PushApi.class);
        ApiHelper helper = mock(ApiHelper.class);
        when(helper.creatApi(PushApi.class)).thenReturn(api);
        try (MockedStatic<ApiHelper> factory = mockStatic(ApiHelper.class)) {
            factory.when(() -> ApiHelper.build(any(GtApiConfiguration.class))).thenReturn(helper);
            service = new PushApiServiceImpl();
        }
    }

    @BeforeEach
    void resetClient() {
        reset(api);
    }

    @Test
    @SuppressWarnings("unchecked")
    void buildsAndroidAndIosNotificationsAndReturnsTaskId() {
        ApiResult<Map<String, Map<String, String>>> result = mock(ApiResult.class);
        when(result.isSuccess()).thenReturn(true);
        when(result.getData()).thenReturn(Map.of("task", Map.of("cid", "task-id")));
        when(api.pushToSingleByCid(any())).thenReturn(result);
        assertThat(service.pushToSingleByCid("cid", "title", "body", "https://example.com")).isEqualTo("task-id");
        ArgumentCaptor<PushDTO<Audience>> request = ArgumentCaptor.forClass(PushDTO.class);
        verify(api).pushToSingleByCid(request.capture());
        var dto = request.getValue();
        assertThat(dto.getRequestId()).matches("[0-9a-f]{32}");
        assertThat(dto.getAudience().getCid()).containsExactly("cid");
        assertThat(dto.getPushMessage().getNotification().getTitle()).isEqualTo("title");
        assertThat(dto.getPushMessage().getNotification().getBody()).isEqualTo("body");
        assertThat(dto.getPushMessage().getNotification().getClickType()).isEqualTo("payload");
        assertThat(JSON.parseObject(dto.getPushMessage().getNotification().getPayload()).getString("url")).isEqualTo("https://example.com");
        assertThat(dto.getPushChannel().getIos().getAps().getAlert().getTitle()).isEqualTo("title");
        assertThat(dto.getPushChannel().getIos().getAutoBadge()).isEqualTo("+1");
        assertThat(dto.getPushChannel().getAndroid().getUps().getNotification().getBody()).isEqualTo("body");
    }

    @Test
    @SuppressWarnings("unchecked")
    void rejectedPushIncludesProviderCodeAndMessage() {
        ApiResult<Map<String, Map<String, String>>> result = mock(ApiResult.class);
        when(result.getCode()).thenReturn(10001);
        when(result.getMsg()).thenReturn("rejected");
        when(api.pushToSingleByCid(any())).thenReturn(result);
        assertThatThrownBy(() -> service.pushToSingleByCid("cid", "title", "body", "url")).hasMessage("code: 10001, msg: rejected");
    }

    @Test
    @SuppressWarnings("unchecked")
    void successfulResponseWithoutTaskIdIsRejected() {
        ApiResult<Map<String, Map<String, String>>> result = mock(ApiResult.class);
        when(result.isSuccess()).thenReturn(true);
        when(result.getData()).thenReturn(Map.of());
        when(api.pushToSingleByCid(any())).thenReturn(result);
        assertThatThrownBy(() -> service.pushToSingleByCid("cid", "title", "body", "url")).isInstanceOf(RuntimeException.class).hasMessageContaining("code:");
    }
}
