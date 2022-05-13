package com.example.demo.weixin.api.bo.wxa;

import com.example.demo.weixin.api.bo.user.Watermark;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = -8347323944841762033L;

    private String            phoneNumber;

    private String            purePhoneNumber;

    private String            countryCode;

    private Watermark         watermark;

}
