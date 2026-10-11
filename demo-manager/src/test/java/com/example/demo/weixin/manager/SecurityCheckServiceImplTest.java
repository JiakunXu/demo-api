package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class SecurityCheckServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("check text", () -> {
            SecurityCheckServiceImpl service = new SecurityCheckServiceImpl();

            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=token"), jsonBody("{\"content\":\"hello\"}")), () -> service.msgSecCheck("token", "{\"content\":\"hello\"}"), BaseResult.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
