package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static com.example.demo.support.HttpContract.jsonBody;
import com.example.demo.weixin.api.bo.wxa.WxaCode;

class WxaCodeServiceImplTest {
    @Test
    void binaryResponse() {
        WxaCode input = JSON.parseObject("{\"scene\":\"id=1\",\"page\":\"pages/index\"}", WxaCode.class);
        byte[] response = new byte[] { (byte) 137, 80, 78, 71 };
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}"))).thenReturn(response);
            assertThat(new WxaCodeServiceImpl().getWxaCodeUnlimit("token", input)).containsExactly(response);
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}")));
        }
    }

    @Test
    void transportResponse() {
        WxaCode input = JSON.parseObject("{\"scene\":\"id=1\",\"page\":\"pages/index\"}", WxaCode.class);
        RuntimeException failure = new RuntimeException("download unavailable");
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}"))).thenThrow(failure);
            assertThatThrownBy(() -> new WxaCodeServiceImpl().getWxaCodeUnlimit("token", input)).hasCause(failure);
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}")));
        }
    }

    @Test
    void providerResponse() {
        WxaCode input = JSON.parseObject("{\"scene\":\"id=1\",\"page\":\"pages/index\"}", WxaCode.class);
        byte[] response = "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}"))).thenReturn(response);
            assertThatThrownBy(() -> new WxaCodeServiceImpl().getWxaCodeUnlimit("token", input)).hasMessage("provider rejected");
            http.verify(() -> HttpUtil.download(eq("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=token"), jsonBody("{\"scene\":\"id=1\",\"page\":\"pages/index\"}")));
        }
    }
}
