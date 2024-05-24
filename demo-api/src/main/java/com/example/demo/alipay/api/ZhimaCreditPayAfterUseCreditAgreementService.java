package com.example.demo.alipay.api;

public interface ZhimaCreditPayAfterUseCreditAgreementService {

    /**
     * 
     * @param outAgreementNo 商户外部协议号，不同的支付宝用户需要传递不同的外部单号
     * @param zmServiceId 芝麻信用服务ID
     * @param categoryId 芝麻外部类目
     * @return
     */
    String sign(String outAgreementNo, String zmServiceId, String categoryId);

    /**
     *
     * @param outAgreementNo 商户外部协议号
     * @param creditAgreementId 芝麻开通/授权协议号
     * @return
     */
    String query(String outAgreementNo, String creditAgreementId);

}
