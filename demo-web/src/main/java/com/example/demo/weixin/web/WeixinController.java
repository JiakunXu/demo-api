package com.example.demo.weixin.web;

import com.example.demo.framework.web.BaseController;
import com.example.demo.weixin.api.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/api/weixin")
public class WeixinController extends BaseController {

    @Autowired
    private ReceivingService receivingService;

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public Long verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echoStr = this.getParameter(request, "echostr");

        return Long.valueOf(receivingService.verify(signature, timestamp, nonce, echoStr));
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String data = this.getParameter(request);

        return receivingService.callback(signature, timestamp, nonce, data);
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticket(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

}
