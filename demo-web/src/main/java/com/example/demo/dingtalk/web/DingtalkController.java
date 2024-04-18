package com.example.demo.dingtalk.web;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.dingtalk.api.DingtalkService;
import com.example.demo.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/dingtalk")
public class DingtalkController extends BaseController {

    @Autowired
    private DingtalkService dingtalkService;

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> callBack(@RequestParam(value = "signature", required = false) String signature,
                                        @RequestParam(value = "timestamp", required = false) String timeStamp,
                                        @RequestParam(value = "nonce", required = false) String nonce,
                                        @RequestBody(required = false) JSONObject data) {
        return dingtalkService.callback(signature, timeStamp, nonce, data.getString("encrypt"));
    }

}
