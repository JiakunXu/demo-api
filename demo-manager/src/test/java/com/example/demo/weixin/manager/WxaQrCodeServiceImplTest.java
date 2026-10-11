package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static com.example.demo.support.HttpContract.jsonBody;
import com.example.demo.weixin.api.bo.wxa.WxaQrCode;

class WxaQrCodeServiceImplTest {
    @Test
    void binaryResponse() {
        WxaQrCode input = JSON.parseObject("{\"path\":\"pages/index\",\"width\":160}", WxaQrCode.class);
        byte[] response = new byte[] { (byte) 137, 80, 78, 71 };
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}"))).thenReturn(response);
            assertThat(new WxaQrCodeServiceImpl().getWxaQrCode("token", input)).containsExactly(response);
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}")));
        }
    }

    @Test
    void transportResponse() {
        WxaQrCode input = JSON.parseObject("{\"path\":\"pages/index\",\"width\":160}", WxaQrCode.class);
        RuntimeException failure = new RuntimeException("download unavailable");
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}"))).thenThrow(failure);
            assertThatThrownBy(() -> new WxaQrCodeServiceImpl().getWxaQrCode("token", input)).hasCause(failure);
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}")));
        }
    }

    @Test
    void providerResponse() {
        WxaQrCode input = JSON.parseObject("{\"path\":\"pages/index\",\"width\":160}", WxaQrCode.class);
        byte[] response = "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}"))).thenReturn(response);
            assertThatThrownBy(() -> new WxaQrCodeServiceImpl().getWxaQrCode("token", input)).hasMessage("provider rejected");
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=token"), jsonBody("{\"path\":\"pages/index\",\"width\":160}")));
        }
    }
}
