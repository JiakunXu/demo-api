package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static com.example.demo.support.HttpContract.jsonBody;
import com.example.demo.bytedance.api.bo.qrcode.Body;
import com.example.demo.bytedance.api.QrCodeService;

class QrCodeServiceImplTest {
    @Test
    void binaryResponse() {
        Body input = JSON.parseObject("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160}", Body.class);
        byte[] response = new byte[] { (byte) 137, 80, 78, 71 };
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}"))).thenReturn(response);
            assertThat(new QrCodeServiceImpl().create("token", input)).containsExactly(response);
            http.verify(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}")));
        }
    }

    @Test
    void transportResponse() {
        Body input = JSON.parseObject("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160}", Body.class);
        RuntimeException failure = new RuntimeException("download unavailable");
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}"))).thenThrow(failure);
            assertThatThrownBy(() -> new QrCodeServiceImpl().create("token", input)).hasCause(failure);
            http.verify(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}")));
        }
    }

    @Test
    void providerResponse() {
        Body input = JSON.parseObject("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160}", Body.class);
        byte[] response = "{\"err_no\":40001,\"errmsg\":\"provider rejected\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}"))).thenReturn(response);
            assertThatThrownBy(() -> new QrCodeServiceImpl().create("token", input)).hasMessage("provider rejected");
            http.verify(() -> HttpUtil.download(eq(QrCodeService.HTTPS_POST_URL), jsonBody("{\"appname\":\"douyin\",\"path\":\"pages/index\",\"width\":160,\"access_token\":\"token\"}")));
        }
    }

    @Test
    void rejectsMissingBody() {
        assertThatThrownBy(() -> new QrCodeServiceImpl().create("token", null)).hasMessage("body is null.");
    }

    @Test
    void rejectsMissingImage() {
        Body input = JSON.parseObject("{\"path\":\"pages/index\"}", Body.class);
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(anyString(), anyString())).thenReturn(null);
            assertThatThrownBy(() -> new QrCodeServiceImpl().create("token", input)).hasMessage("qr_code is null.");
        }
    }
}
