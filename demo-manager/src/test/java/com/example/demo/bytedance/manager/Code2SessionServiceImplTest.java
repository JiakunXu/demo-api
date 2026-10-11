package com.example.demo.bytedance.manager;

import com.example.demo.bytedance.api.bo.session.Session;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class Code2SessionServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("session", () -> {
            Code2SessionServiceImpl service = new Code2SessionServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://developer.toutiao.com/api/apps/jscode2session?appid=app&secret=secret&code=code"), () -> service.getSession("app", "secret", "code"), Session.class,
                "{\"openid\":\"open\",\"session_key\":\"key\"}", "{\"openid\":\"open\",\"session_key\":\"key\"}", "{\"err_no\":40001,\"err_tips\":\"provider rejected\"}", "session is null.");
        });
    }
}
