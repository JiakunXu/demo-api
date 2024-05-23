package com.example.demo.captcha.web;

import com.example.demo.captcha.api.CaptchaService;
import com.example.demo.captcha.api.bo.Captcha;
import com.example.demo.config.api.ConfigService;
import com.example.demo.config.api.bo.Config;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController extends BaseController {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private ConfigService  configService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<Captcha> get(HttpServletRequest request, HttpServletResponse response) {
        Config config = configService.getConfig("", "sys.account.captchaOnOff");

        if (config == null || "true".equals(config.getValue())) {
            return new ObjectResponse<>(captchaService.getCaptcha());
        }

        return new ObjectResponse<>(new Captcha());
    }

}
