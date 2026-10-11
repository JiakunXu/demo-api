package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.bo.js.JsapiTicket;
import com.example.demo.framework.util.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.stream.Stream;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.mockito.ArgumentMatchers.eq;

class JsapiTicketServiceImplTest {
    @TestFactory
    Stream<DynamicTest> endpoint1() {
        return HttpContract.json("ticket", () -> {
            JsapiTicketServiceImpl service = new JsapiTicketServiceImpl();

            return new Endpoint(() -> HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=token&type=jsapi"), () -> service.getJsapiTicket("token"), JsapiTicket.class,
                "{\"ticket\":\"ticket-value\",\"expires_in\":7200}", "{\"ticket\":\"ticket-value\",\"expires_in\":7200}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "jsapi_ticket is null.");
        });
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.NullAndEmptySource
    @org.junit.jupiter.params.provider.ValueSource(strings = {"   "})
    void rejectsMissingTicketEvenWhenProviderReportsSuccess(String ticket) {
        String response = com.alibaba.fastjson2.JSON.toJSONString(java.util.Map.of("errcode", 0, "ticket", ticket == null ? "" : ticket));
        try (var http = org.mockito.Mockito.mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.get(org.mockito.ArgumentMatchers.anyString())).thenReturn(response);
            org.assertj.core.api.Assertions.assertThatThrownBy(() -> new JsapiTicketServiceImpl().getJsapiTicket("token"))
                .hasMessage("ticket is blank.");
        }
    }
}
