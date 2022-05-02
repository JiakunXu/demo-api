package com.example.demo.bytedance.web;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.MessageService;
import com.example.demo.bytedance.api.bo.Callback;
import com.example.demo.framework.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/api/bytedance")
public class BytedanceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BytedanceController.class);

    @Autowired
    private MessageService      messageService;

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echostr = this.getParameter(request, "echostr");

        try {
            response.getWriter().write(messageService.verify(signature, timestamp, nonce, echostr));
        } catch (Exception e) {
            logger.error("verify", e);
        }
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        Callback callback = JSON.parseObject(this.getParameter(request), Callback.class);

        try {
            messageService.callback(callback.getMsgSignature(), callback.getTimestamp(),
                callback.getNonce(), callback.getMsg());
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.error("callback", e);
        }
    }

}
