package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.device.Device;
import com.example.demo.weixin.api.bo.device.SnTicket;
import com.example.demo.weixin.api.bo.device.Message;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class HardwareDeviceServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("device ticket", () -> {
            HardwareDeviceServiceImpl service = new HardwareDeviceServiceImpl();
            Device device = JSON.parseObject("{\"sn\":\"serial-1\",\"model_id\":\"model-1\"}", Device.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/getsnticket?access_token=token"), jsonBody("{\"sn\":\"serial-1\",\"model_id\":\"model-1\"}")), () -> service.getSnTicket("token", device), SnTicket.class,
                "{\"sn_ticket\":\"device-ticket\"}", "{\"sn_ticket\":\"device-ticket\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "snTicket is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> endpoint2() {
        return HttpContract.json("device message", () -> {
            HardwareDeviceServiceImpl service = new HardwareDeviceServiceImpl();
            Message message = JSON.parseObject("{\"to_openid_list\":[\"openid\"],\"template_id\":\"template\",\"sn\":\"serial-1\",\"page\":\"pages/index\"}", Message.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/device/subscribe/send?access_token=token"), jsonBody("{\"to_openid_list\":[\"openid\"],\"template_id\":\"template\",\"sn\":\"serial-1\",\"page\":\"pages/index\"}")), () -> service.send("token", message), BaseResult.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
