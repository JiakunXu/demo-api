package com.example.demo.framework.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxpayConfigurer {

    @Value("${wxpay.apiV3Key}")
    private String apiV3Key;

    @Value("${wxpay.merchant.id}")
    private String merchantId;

    @Value("${wxpay.merchant.serialNumber}")
    private String serialNumber;

    @Value("${wxpay.privateKey.path}")
    private String privateKeyPath;

    @Value("${wxpay.partner.apiV3Key}")
    private String partnerApiV3Key;

    @Value("${wxpay.partner.merchant.id}")
    private String partnerMerchantId;

    @Value("${wxpay.partner.merchant.serialNumber}")
    private String partnerSerialNumber;

    @Value("${wxpay.partner.privateKey.path}")
    private String partnerPrivateKeyPath;

    @Bean(name = "merchantConfig")
    public Config init0() {
        try {
            return new RSAAutoCertificateConfig.Builder().merchantId(merchantId)
                .privateKeyFromPath(privateKeyPath).merchantSerialNumber(serialNumber)
                .apiV3Key(apiV3Key).build();
        } catch (Exception e) {
            // throw new RuntimeException(e);
        }

        return null;
    }

    @Bean(name = "partnerConfig")
    public Config init1() {
        try {
            return new RSAAutoCertificateConfig.Builder().merchantId(partnerMerchantId)
                .privateKeyFromPath(partnerPrivateKeyPath).merchantSerialNumber(partnerSerialNumber)
                .apiV3Key(partnerApiV3Key).build();
        } catch (Exception e) {
            // throw new RuntimeException(e);
        }

        return null;
    }

}
