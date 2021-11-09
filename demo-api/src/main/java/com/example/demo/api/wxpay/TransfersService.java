package com.example.demo.api.wxpay;

import com.example.demo.api.wxpay.ao.Transfers;

public interface TransfersService {

    String HTTPS_TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     *
     * @param transfers
     * @param key
     * @param sslPath
     * @return
     * @throws RuntimeException
     */
    Transfers transfers(Transfers transfers, String key, String sslPath) throws RuntimeException;

}
