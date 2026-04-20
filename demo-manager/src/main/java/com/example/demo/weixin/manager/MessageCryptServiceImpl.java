package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.MessageCryptService;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class MessageCryptServiceImpl implements MessageCryptService {

    /**
     *
     * @param token
     * @param encodingAesKey
     * @param appid
     * @return
     */
    private WXBizMsgCrypt init(String token, String encodingAesKey, String appid) {
        try {
            return new WXBizMsgCrypt(token, encodingAesKey, appid);
        } catch (AesException e) {
            log.error("{},{},{}", token, encodingAesKey, appid, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String verify(String token, String encodingAesKey, String appid, String signature,
                         String timestamp, String nonce, String echoStr) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.verifyUrl(signature, timestamp, nonce, echoStr);
        } catch (AesException e) {
            log.error("{},{},{},{}", signature, timestamp, nonce, echoStr, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String token, String encodingAesKey, String appid, String signature,
                          String timestamp, String nonce, String data) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.decryptMsg(signature, timestamp, nonce, data);
        } catch (AesException e) {
            log.error("{},{},{},{}", signature, timestamp, nonce, data, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public String encrypt(String token, String encodingAesKey, String appid, String data,
                          String timestamp, String nonce) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.encryptMsg(data, timestamp, nonce);
        } catch (AesException e) {
            log.error("{},{},{}", data, timestamp, nonce, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
