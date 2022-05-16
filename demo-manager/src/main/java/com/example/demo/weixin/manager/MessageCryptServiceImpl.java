package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.MessageCryptService;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class MessageCryptServiceImpl implements MessageCryptService {

    private static final Logger logger = LoggerFactory.getLogger(MessageCryptServiceImpl.class);

    /**
     *
     * @param token
     * @param encodingAesKey
     * @param appid
     * @return
     */
    private WXBizMsgCrypt init(String token, String encodingAesKey, String appid) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("token cannot be null.");
        }

        if (StringUtils.isBlank(encodingAesKey)) {
            throw new RuntimeException("encoding_aes_key cannot be null.");
        }

        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid cannot be null.");
        }

        try {
            return new WXBizMsgCrypt(token, encodingAesKey, appid);
        } catch (AesException e) {
            logger.error(token + "&" + encodingAesKey + "&" + appid, e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public String verify(String token, String encodingAesKey, String appid, String signature,
                         String timestamp, String nonce, String echoStr) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.verifyUrl(signature, timestamp, nonce, echoStr);
        } catch (AesException e) {
            logger.error(signature + "&" + timestamp + "&" + nonce + "&" + echoStr, e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String token, String encodingAesKey, String appid, String signature,
                          String timestamp, String nonce, String data) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.decryptMsg(signature, timestamp, nonce, data);
        } catch (AesException e) {
            logger.error(signature + "&" + timestamp + "&" + nonce + "&" + data, e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String token, String encodingAesKey, String appid, String data,
                          String timestamp, String nonce) throws RuntimeException {
        WXBizMsgCrypt crypt = init(token, encodingAesKey, appid);

        try {
            return crypt.encryptMsg(data, timestamp, nonce);
        } catch (AesException e) {
            logger.error(data + "&" + timestamp + "&" + nonce, e);

            throw new RuntimeException(e);
        }
    }

}
