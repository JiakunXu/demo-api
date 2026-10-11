package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.wxa.OpenLink;
import com.example.demo.weixin.api.bo.wxa.UrlScheme;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class UrlSchemeServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("url scheme", () -> {
            UrlSchemeServiceImpl service = new UrlSchemeServiceImpl();
            UrlScheme scheme = JSON.parseObject("{\"jump_wxa\":{\"path\":\"pages/index\",\"query\":\"id=1\"},\"expire_type\":1,\"expire_interval\":7}", UrlScheme.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/generatescheme?access_token=token"), jsonBody("{\"jump_wxa\":{\"path\":\"pages/index\",\"query\":\"id=1\"},\"expire_type\":1,\"expire_interval\":7}")), () -> service.generateScheme("token", scheme), OpenLink.class,
                "{\"openlink\":\"weixin://test\"}", "{\"openlink\":\"weixin://test\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "open_link is null.");
        });
    }
}
