package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.Transaction;

public interface TransactionsService {

    String HTTPS_JSAPI_URL = "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/jsapi";

    String HTTPS_H5_URL    = "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/h5";

    String HTTPS_TRADE_URL = "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/out-trade-no";

    /**
     *
     * @param transaction
     * @return
     * @throws RuntimeException
     */
    String getPrepayId(Transaction transaction) throws RuntimeException;

    /**
     * 
     * @param transaction
     * @return
     * @throws RuntimeException
     */
    String getH5Url(Transaction transaction) throws RuntimeException;

    String get(String spMchid, String subMchid, String outTradeNo) throws RuntimeException;

    String close(String spMchid, String subMchid, String outTradeNo) throws RuntimeException;

}
