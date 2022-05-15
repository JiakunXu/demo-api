package com.example.demo.weixin.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.weixin.api.MessageCryptService;
import com.example.demo.weixin.api.ReceivingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class ReceivingServiceImpl implements ReceivingService {

    @Autowired
    private MessageCryptService messageCryptService;

    @Value("${weixin.token}")
    private String              token;

    @Value("${weixin.encodingAesKey}")
    private String              encodingAesKey;

    @Value("${weixin.app.id}")
    private String              appId;

    @Override
    public String verify(String signature, String timestamp, String nonce,
                         String echoStr) throws RuntimeException {
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
            || StringUtils.isBlank(nonce)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        return messageCryptService.verify(token, encodingAesKey, appId, signature, timestamp, nonce,
            echoStr);
    }

}
