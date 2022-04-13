package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.Transfers;

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
