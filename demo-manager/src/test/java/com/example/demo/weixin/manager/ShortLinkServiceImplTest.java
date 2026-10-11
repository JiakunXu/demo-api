package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.wxa.Link;
import com.example.demo.weixin.api.bo.wxa.Page;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class ShortLinkServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("short link", () -> {
            ShortLinkServiceImpl service = new ShortLinkServiceImpl();
            Page page = JSON.parseObject("{\"page_url\":\"pages/index\",\"page_title\":\"首页\",\"is_permanent\":true}", Page.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/genwxashortlink?access_token=token"), jsonBody("{\"page_url\":\"pages/index\",\"page_title\":\"首页\",\"is_permanent\":true}")), () -> service.generateShortLink("token", page), Link.class,
                "{\"link\":\"https://wxaurl.cn/test\"}", "{\"link\":\"https://wxaurl.cn/test\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "link is null.");
        });
    }
}
