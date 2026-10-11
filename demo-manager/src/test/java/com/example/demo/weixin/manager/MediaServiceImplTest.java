package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static com.example.demo.support.HttpContract.jsonBody;

class MediaServiceImplTest {
    @Test
    void binaryResponse() {

        byte[] response = new byte[] { (byte) 137, 80, 78, 71 };
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download("https://api.weixin.qq.com/cgi-bin/media/get?access_token=token&media_id=media-id")).thenReturn(response);
            assertThat(new MediaServiceImpl().getMedia("token", "media-id")).containsExactly(response);
            http.verify(() -> HttpUtil.download("https://api.weixin.qq.com/cgi-bin/media/get?access_token=token&media_id=media-id"));
        }
    }

    @Test
    void transportResponse() {

        RuntimeException failure = new RuntimeException("download unavailable");
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.download("https://api.weixin.qq.com/cgi-bin/media/get?access_token=token&media_id=media-id")).thenThrow(failure);
            assertThatThrownBy(() -> new MediaServiceImpl().getMedia("token", "media-id")).hasCause(failure);
            http.verify(() -> HttpUtil.download("https://api.weixin.qq.com/cgi-bin/media/get?access_token=token&media_id=media-id"));
        }
    }
}
