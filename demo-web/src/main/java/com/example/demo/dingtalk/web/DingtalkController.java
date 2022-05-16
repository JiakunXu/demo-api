package com.example.demo.dingtalk.web;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.dingtalk.api.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/api/dingtalk")
public class DingtalkController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
                                        @RequestParam(value = "timestamp", required = false) String timeStamp,
                                        @RequestParam(value = "nonce", required = false) String nonce,
                                        @RequestBody(required = false) JSONObject json) {
        return eventService.callback("", signature, timeStamp, nonce, json.getString("encrypt"));
    }

}
