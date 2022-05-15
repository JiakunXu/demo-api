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

    @Override
    public String verify(String token, String encodingAesKey, String appid, String signature,
                         String timestamp, String nonce, String echostr) throws RuntimeException {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("token cannot be null.");
        }

        if (StringUtils.isNotBlank(encodingAesKey)) {
            throw new RuntimeException("encoding_aes_key cannot be null.");
        }

        if (StringUtils.isNotBlank(appid)) {
            throw new RuntimeException("appid cannot be null.");
        }

        WXBizMsgCrypt wxcpt;

        try {
            wxcpt = new WXBizMsgCrypt(token, encodingAesKey, appid);
        } catch (AesException e) {
            logger.error("token" + "&" + "encodingAesKey" + "&" + "appid", e);

            throw new RuntimeException(e.getMessage());
        }

        try {
            return wxcpt.verifyUrl(signature, timestamp, nonce, echostr);
        } catch (AesException e) {
            logger.error("signature" + "&" + "timestamp" + "&" + "nonce" + "&" + echostr, e);

            throw new RuntimeException(e.getMessage());
        }
    }

}
