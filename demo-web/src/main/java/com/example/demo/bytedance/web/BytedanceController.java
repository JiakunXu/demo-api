package com.example.demo.bytedance.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.bytedance.api.MessageService;
import com.example.demo.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/api/bytedance")
public class BytedanceController extends BaseController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/callback", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echostr = this.getParameter(request, "echostr");

        return messageService.verify(signature, timestamp, nonce, echostr);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = JSON.parseObject(this.getParameter(request));

        messageService.callback(json.getString("msg_signature"), json.getString("timestamp"),
            json.getString("nonce"), json.getString("msg"));

        return "success";
    }

}
