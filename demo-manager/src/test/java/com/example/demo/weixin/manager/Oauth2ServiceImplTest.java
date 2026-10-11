package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.Oauth2Service;
import com.example.demo.weixin.api.bo.sns.AccessToken;
import com.example.demo.weixin.api.bo.sns.UserInfo;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class Oauth2ServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("oauth token", () -> {
            Oauth2ServiceImpl service = new Oauth2ServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=app&secret=secret&code=code"), () -> service.getAccessToken("app", "secret", "code"), AccessToken.class,
                "{\"access_token\":\"token\",\"openid\":\"open\",\"expires_in\":7200}", "{\"access_token\":\"token\",\"openid\":\"open\",\"expires_in\":7200}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "access_token is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> endpoint2() {
        return HttpContract.json("user en", () -> {
            Oauth2ServiceImpl service = new Oauth2ServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/sns/userinfo?access_token=token&openid=open&lang=en"), () -> service.getUserInfo("token", "open", "en"), UserInfo.class,
                "{\"openid\":\"open\",\"nickname\":\"测试用户\",\"sex\":1}", "{\"openid\":\"open\",\"nickname\":\"测试用户\",\"sex\":1}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "userinfo is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> endpoint3() {
        return HttpContract.json("user None", () -> {
            Oauth2ServiceImpl service = new Oauth2ServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/sns/userinfo?access_token=token&openid=open&lang=zh_CN"), () -> service.getUserInfo("token", "open", null), UserInfo.class,
                "{\"openid\":\"open\",\"nickname\":\"测试用户\",\"sex\":1}", "{\"openid\":\"open\",\"nickname\":\"测试用户\",\"sex\":1}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "userinfo is null.");
        });
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {"snsapi_login", "snsapi_userinfo"})
    void constructsAuthorizationUrlForScope(String scope) {
        String endpoint = "snsapi_login".equals(scope) ? Oauth2Service.HTTPS_QRCONNECT_URL : Oauth2Service.HTTPS_AUTHORIZE_URL;
        org.assertj.core.api.Assertions.assertThat(new Oauth2ServiceImpl().authorize("app", "https%3A%2F%2Fexample.com", scope, "state"))
            .isEqualTo(endpoint + "?appid=app&redirect_uri=https%3A%2F%2Fexample.com&response_type=code&scope=" + scope + "&state=state#wechat_redirect");
    }
}
