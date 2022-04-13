package com.example.demo.weixin.web;

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

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String ticket(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

}
