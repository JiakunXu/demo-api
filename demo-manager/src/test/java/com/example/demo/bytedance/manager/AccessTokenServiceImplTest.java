package com.example.demo.bytedance.manager;

import com.example.demo.bytedance.api.AccessTokenService;
import com.example.demo.bytedance.api.bo.token.AccessToken;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class AccessTokenServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("token", () -> {
            AccessTokenServiceImpl service = new AccessTokenServiceImpl();

            return new Endpoint(() -> HttpUtil.post(eq(AccessTokenService.HTTPS_POST_URL), jsonBody("{\"appid\":\"app\",\"secret\":\"secret\",\"grant_type\":\"client_credential\"}")), () -> service.getAccessToken("app", "secret", "client_credential"), AccessToken.class,
                "{\"data\":{\"access_token\":\"token\",\"expires_in\":7200}}", "{\"access_token\":\"token\",\"expires_in\":7200}", "{\"err_no\":40001,\"err_tips\":\"provider rejected\"}", "access_token is null.");
        });
    }
}
