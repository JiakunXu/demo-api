package com.example.demo.weixin.manager;

import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.bo.qrcode.QrCode;
import com.example.demo.weixin.api.bo.qrcode.Result;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class QrCodeServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("qr code", () -> {
            QrCodeServiceImpl service = new QrCodeServiceImpl();
            QrCode qr = JSON.parseObject("{\"expire_seconds\":60,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\"scene-1\"}}}", QrCode.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=token"), jsonBody("{\"expire_seconds\":60,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\"scene-1\"}}}")), () -> service.create("token", qr), Result.class,
                "{\"ticket\":\"qr-ticket\",\"expire_seconds\":60,\"url\":\"https://qr.test\"}", "{\"ticket\":\"qr-ticket\",\"expire_seconds\":60,\"url\":\"https://qr.test\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }
}
