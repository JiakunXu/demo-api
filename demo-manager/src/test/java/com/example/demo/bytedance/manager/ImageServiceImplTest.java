package com.example.demo.bytedance.manager;

import com.example.demo.bytedance.api.ImageService;
import com.example.demo.bytedance.api.bo.text.Body;
import com.example.demo.bytedance.api.bo.text.Log;
import com.example.demo.framework.util.HttpUtil;
import java.util.Map;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class ImageServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("detect", () -> {
            ImageServiceImpl service = new ImageServiceImpl();
            Body body = JSON.parseObject("{\"tasks\":[{\"content\":\"hello\"}]}", Body.class);
            return new Endpoint(() -> HttpUtil.post(eq(ImageService.HTTPS_POST_URL), jsonBody("{\"tasks\":[{\"content\":\"hello\"}]}"), eq(java.util.Map.of("X-Token", "token"))), () -> service.detect("token", body), Log.class,
                "{\"log_id\":\"log-1\",\"data\":[{\"code\":0,\"msg\":\"ok\"}]}", "{\"log_id\":\"log-1\",\"data\":[{\"code\":0,\"msg\":\"ok\"}]}", "{\"code\":\"40001\",\"message\":\"provider rejected\"}", "log is null.");
        });
    }
}
