package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.device.Hardware;
import com.example.demo.weixin.api.bo.device.SnTicket;
import com.example.demo.weixin.api.bo.message.DeviceMsg;

/**
 * @author JiakunXu
 */
public interface HardwareDeviceService {

    String HTTPS_GET_URL  = "https://api.weixin.qq.com/wxa/getsnticket?access_token=";

    String HTTPS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/device/subscribe/send?access_token=";

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

    /**
     * 开发者可以通过该接口向用户发送设备消息.
     * 
     * @param accessToken
     * @param deviceMsg
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/hardware-device/hardwareDevice.send.html">微信官方文档</a>
     */
    BaseResult send(String accessToken, DeviceMsg deviceMsg) throws RuntimeException;

}
