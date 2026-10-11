package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Activity;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class UpdatableMessageServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("activity", () -> {
            UpdatableMessageServiceImpl service = new UpdatableMessageServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/cgi-bin/message/wxopen/activityid/create?access_token=token&unionid=union&openid=open"), () -> service.createActivityId("token", "union", "open"), Activity.class,
                "{\"activity_id\":\"activity\",\"expiration_time\":1234}", "{\"activity_id\":\"activity\",\"expiration_time\":1234}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "activity is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> endpoint2() {
        return HttpContract.json("update activity", () -> {
            UpdatableMessageServiceImpl service = new UpdatableMessageServiceImpl();
            Activity activity = JSON.parseObject("{\"target_state\":1}", Activity.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/wxopen/updatablemsg/send?access_token=token"), jsonBody("{\"activity_id\":\"activity\",\"target_state\":1}")), () -> service.setUpdatableMsg("token", "activity", activity), BaseResult.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @org.junit.jupiter.api.Test
    void rejectsMissingActivityBeforeSending() {
        try (var http = org.mockito.Mockito.mockStatic(HttpUtil.class)) {
            org.assertj.core.api.Assertions.assertThatThrownBy(() -> new UpdatableMessageServiceImpl().setUpdatableMsg("token", "activity", null))
                .hasMessage("activity cannot be null.");
            http.verifyNoInteractions();
        }
    }
}
