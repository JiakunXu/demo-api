package com.example.demo.api.wxpay;

import com.example.demo.api.wxpay.ao.UnifiedOrder;

/**
 * @author JiakunXu
 */
public interface UnifiedOrderService {

    String HTTPS_UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     *
     * @param unifiedOrder
     * @param key
     * @return
     * @throws RuntimeException
     */
    String getPrepayId(UnifiedOrder unifiedOrder, String key) throws RuntimeException;

}
