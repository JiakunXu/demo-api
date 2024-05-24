package com.example.demo.alipay.api;

public interface AlipayAesService {

    String decrypt(String cipherText);

    String encrypt(String plainText);

}
