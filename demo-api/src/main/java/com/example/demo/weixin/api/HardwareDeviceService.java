package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.device.Hardware;
import com.example.demo.weixin.api.bo.device.SnTicket;

/**
 * @author JiakunXu
 */
public interface HardwareDeviceService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/wxa/getsnticket?access_token=";

    /**
     * 获取设备票据，5 分钟内有效.
     * 
     * @param accessToken
     * @param hardware
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/hardware-device/hardwareDevice.getSnTicket.html">微信官方文档</a>
     */
    SnTicket getSnTicket(String accessToken, Hardware hardware) throws RuntimeException;

}
