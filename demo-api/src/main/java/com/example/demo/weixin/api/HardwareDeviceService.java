package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.device.Device;
import com.example.demo.weixin.api.bo.device.SnTicket;
import com.example.demo.weixin.api.bo.device.Message;

/**
 * @author JiakunXu
 */
public interface HardwareDeviceService {

    String HTTPS_GET_URL  = "https://api.weixin.qq.com/wxa/getsnticket?access_token={0}";

    String HTTPS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/device/subscribe/send?access_token={0}";

    /**
     * 获取设备票据，5 分钟内有效.
     * 
     * @param accessToken
     * @param device
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/hardware-device/hardwareDevice.getSnTicket.html">微信官方文档</a>
     */
    SnTicket getSnTicket(String accessToken, Device device) throws RuntimeException;

    /**
     * 开发者可以通过该接口向用户发送设备消息.
     * 
     * @param accessToken
     * @param message
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/hardware-device/hardwareDevice.send.html">微信官方文档</a>
     */
    BaseResult send(String accessToken, Message message) throws RuntimeException;

}
