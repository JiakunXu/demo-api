package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.wxa.UserInfo;
import com.example.demo.weixin.api.bo.wxa.UserRisk;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class UserRiskRankServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("user risk", () -> {
            UserRiskRankServiceImpl service = new UserRiskRankServiceImpl();
            UserInfo info = JSON.parseObject("{\"openid\":\"openid\",\"scene\":1}", UserInfo.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/getuserriskrank?access_token=token"), jsonBody("{\"appid\":\"app\",\"openid\":\"openid\",\"scene\":1}")), () -> service.getUserRiskRank("app", "token", info), UserRisk.class,
                "{\"risk_rank\":1}", "{\"risk_rank\":1}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "userRisk is null.");
        });
    }
}
