package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.material.Count;
import com.example.demo.weixin.api.bo.material.Material;
import com.example.demo.weixin.api.bo.material.Parameter;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class MaterialServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("count", () -> {
            MaterialServiceImpl service = new MaterialServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=token"), () -> service.count("token"), Count.class,
                "{\"image_count\":3,\"voice_count\":2,\"video_count\":1,\"news_count\":4}", "{\"image_count\":3,\"voice_count\":2,\"video_count\":1,\"news_count\":4}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "count is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> endpoint2() {
        return HttpContract.json("list", () -> {
            MaterialServiceImpl service = new MaterialServiceImpl();
            Parameter parameter = JSON.parseObject("{\"type\":\"image\",\"offset\":2,\"count\":5}", Parameter.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=token"), jsonBody("{\"type\":\"image\",\"offset\":2,\"count\":5}")), () -> service.list("token", parameter), Material.class,
                "{\"total_count\":9,\"item_count\":1,\"item\":[{\"media_id\":\"media-1\",\"name\":\"image\"}]}", "{\"total_count\":9,\"item_count\":1,\"item\":[{\"media_id\":\"media-1\",\"name\":\"image\"}]}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "material is null.");
        });
    }
}
