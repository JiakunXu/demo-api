package com.example.demo.bytedance.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.bytedance.api.BytedanceNotifyService;
import com.example.demo.framework.web.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/bytedance")
public class BytedanceController extends BaseController {

    @Autowired
    private BytedanceNotifyService bytedanceNotifyService;

    @RequestMapping(value = "/notify", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echostr = this.getParameter(request, "echostr");

        return bytedanceNotifyService.verify(signature, timestamp, nonce, echostr);
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = JSON.parseObject(this.getParameter(request));

        bytedanceNotifyService.notify(data.getString("msg_signature"), data.getString("timestamp"),
            data.getString("nonce"), data.getString("msg"));

        return "success";
    }

}
