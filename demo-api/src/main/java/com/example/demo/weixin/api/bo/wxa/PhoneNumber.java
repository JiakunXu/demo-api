package com.example.demo.weixin.api.bo.wxa;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class PhoneNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = -8347323944841762033L;

    private String            phoneNumber;

    private String            purePhoneNumber;

    private String            countryCode;

    private Watermark         watermark;

    @Getter
    @Setter
    public static class Watermark implements Serializable {

        @Serial
        private static final long serialVersionUID = -946609595011819810L;

        /**
         * 敏感数据归属 appId，开发者可校验此参数与自身 appId 是否一致.
         */
        private String            appid;

        /**
         * 敏感数据获取的时间戳, 开发者可以用于数据时效性校验.
         */
        private int               timestamp;

    }

}
