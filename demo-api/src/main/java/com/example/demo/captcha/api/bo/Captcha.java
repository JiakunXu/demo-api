package com.example.demo.captcha.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Captcha implements Serializable {

    @Serial
    private static final long serialVersionUID = -4002187554223623221L;

    private Boolean           captchaEnabled;

    private String            uuid;

    private String            img;

    public Captcha(String uuid, String img) {
        this.captchaEnabled = true;
        this.uuid = uuid;
        this.img = img;
    }

    public Captcha() {
        this.captchaEnabled = false;
    }
}