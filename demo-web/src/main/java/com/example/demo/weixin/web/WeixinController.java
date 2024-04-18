package com.example.demo.weixin.web;

import com.example.demo.framework.web.BaseController;
import com.example.demo.weixin.api.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/api/weixin")
public class WeixinController extends BaseController {

    @Autowired
    private ReceivingService receivingService;

    @RequestMapping(value = "/notify", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echoStr = this.getParameter(request, "echostr");

        return receivingService.verify(signature, timestamp, nonce, echoStr);
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String data = this.getParameter(request);

        return receivingService.notify(signature, timestamp, nonce, data);
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticket(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

}
