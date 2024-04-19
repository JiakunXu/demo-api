package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.WxaQrCode;

/**
 * @author JiakunXu
 */
public interface WxaQrCodeService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token={0}";

    /**
     * 
     * @param accessToken
     * @param wxaQrCode
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.createQRCode.html">微信官方文档</a>
     */
    byte[] getWxaQrCode(String accessToken, WxaQrCode wxaQrCode) throws RuntimeException;

}
