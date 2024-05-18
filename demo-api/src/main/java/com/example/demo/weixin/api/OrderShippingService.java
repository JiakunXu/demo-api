package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.ShippingInfo;

public interface OrderShippingService {

    String HTTPS_POST_URL = "https://api.weixin.qq.com/wxa/sec/order/upload_shipping_info?access_token={0}";

    /**
     *
     * @param accessToken
     * @param shippingInfo
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/platform-capabilities/business-capabilities/order-shipping/order-shipping.html">微信官方文档</a>
     */
    BaseResult uploadShippingInfo(String accessToken,
                                  ShippingInfo shippingInfo) throws RuntimeException;

}
