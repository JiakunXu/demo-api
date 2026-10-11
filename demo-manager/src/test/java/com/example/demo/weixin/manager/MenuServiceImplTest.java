package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.menu.Menu;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class MenuServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("create menu", () -> {
            MenuServiceImpl service = new MenuServiceImpl();
            Menu menu = JSON.parseObject("{\"button\":[{\"type\":\"click\",\"name\":\"菜单\",\"key\":\"menu-key\"}]}", Menu.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=token"), jsonBody("{\"button\":[{\"type\":\"click\",\"name\":\"菜单\",\"key\":\"menu-key\"}]}")), () -> service.create("token", menu), BaseResult.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
