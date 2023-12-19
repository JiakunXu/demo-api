package com.example.demo.framework.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class AlipayConfigurer {

    @Value("${alipay.app.id}")
    private String appId;

    @Value("${alipay.privateKey.path}")
    private String privateKeyPath;

    @Value("${alipay.merchantCert.path}")
    private String merchantCertPath;

    @Value("${alipay.cert.path}")
    private String alipayCertPath;

    @Value("${alipay.rootCert.path}")
    private String alipayRootCertPath;

    @Value("${alipay.notify.url}")
    private String notifyUrl;

    @Value("${alipay.encrypt.key}")
    private String encryptKey;

    @Bean
    public String init() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.appId = appId;
        config.signType = "RSA2";

        try {
            config.merchantPrivateKey = IOUtils
                .toString(Files.newInputStream(Paths.get(privateKeyPath)), StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

            config.merchantCertPath = merchantCertPath;
            config.alipayCertPath = alipayCertPath;
            config.alipayRootCertPath = alipayRootCertPath;
            config.notifyUrl = notifyUrl;
            config.encryptKey = encryptKey;

            Factory.setOptions(config);
        } catch (Exception e) {
            // throw new RuntimeException(e.getMessage(), e);
        }

        return null;
    }

}
