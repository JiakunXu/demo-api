package com.example.demo.alipay.api;

import java.math.BigDecimal;

public interface ZhimaCreditPayAfterUseCreditBizOrderService {

    /**
     * 
     * @param outOrderNo 商户外部订单号，保证不重复
     * @param creditAgreementId 芝麻开通协议号
     * @param categoryId 芝麻外部类目
     * @param subject 订单标题
     * @return
     */
    String order(String outOrderNo, String creditAgreementId, String categoryId,
                 BigDecimal orderAmount, String subject);

    /**
     * 
     * @param outOrderNo 商户外部订单号，保证不重复
     * @param zmServiceId 芝麻信用服务ID
     * @param categoryId 芝麻外部类目
     * @param subject 订单标题
     * @return
     */
    String create(String outOrderNo, BigDecimal orderAmount, String zmServiceId, String categoryId,
                  String subject);

    /**
     *
     * @param creditBizOrderId 信用服务订单号
     * @param outOrderNo 商户外部单号
     * @return
     */
    String query(String creditBizOrderId, String outOrderNo);

    /**
     * 
     * @param outRequestNo 商户外部请求号
     * @param creditBizOrderId 信用服务订单号
     * @param isFulfilled 用户此订单是否守约
     * @param remark 商户对本次操作的附言描述
     * @return
     */
    String finish(String outRequestNo, String creditBizOrderId, String isFulfilled, String remark);

}
