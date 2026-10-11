package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.ShippingInfo;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class OrderShippingServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("shipping", () -> {
            OrderShippingServiceImpl service = new OrderShippingServiceImpl();
            ShippingInfo info = JSON.parseObject("{\"order_key\":{\"order_number_type\":2,\"transaction_id\":\"txn\"},\"logistics_type\":1,\"delivery_mode\":1}", ShippingInfo.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/sec/order/upload_shipping_info?access_token=token"), jsonBody("{\"order_key\":{\"order_number_type\":2,\"transaction_id\":\"txn\"},\"logistics_type\":1,\"delivery_mode\":1}")), () -> service.uploadShippingInfo("token", info), BaseResult.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
