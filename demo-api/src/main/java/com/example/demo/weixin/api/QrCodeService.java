package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.qrcode.QrCode;
import com.example.demo.weixin.api.bo.qrcode.Result;

/**
 * @author JiakunXu
 */
public interface QrCodeService {

    String HTTPS_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";

    /**
     * 
     * @param accessToken
     * @param qrCode
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html">微信官方文档</a>
     */
    Result create(String accessToken, QrCode qrCode) throws RuntimeException;

}
