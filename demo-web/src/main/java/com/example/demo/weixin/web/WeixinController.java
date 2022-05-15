package com.example.demo.weixin.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.framework.web.BaseController;
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

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String signature = this.getParameter(request, "signature");
        String timestamp = this.getParameter(request, "timestamp");
        String nonce = this.getParameter(request, "nonce");
        String echostr = this.getParameter(request, "echostr");

        return "success";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = JSON.parseObject(this.getParameter(request));

        return "success";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticket(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

}
