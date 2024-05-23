package com.example.demo.captcha.api;

import com.example.demo.captcha.api.bo.Captcha;

public interface CaptchaService {

    Captcha getCaptcha();

    void validate(String uuid, String code);

}
