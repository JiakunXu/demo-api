package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.example.demo.alipay.api.AlipayAesService;
import org.springframework.stereotype.Service;

@Service
public class AlipayAesServiceImpl implements AlipayAesService {

    @Override
    public String decrypt(String cipherText) {
        try {
            return Factory.Util.AES().decrypt(cipherText);
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String encrypt(String plainText) {
        try {
            return Factory.Util.AES().encrypt(plainText);
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
