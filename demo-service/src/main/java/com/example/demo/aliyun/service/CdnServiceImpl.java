package com.example.demo.aliyun.service;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.aliyun.api.CdnService;
import com.example.demo.framework.util.EncryptUtil;

/**
 * @author JiakunXu
 */
@Service
public class CdnServiceImpl implements CdnService {

    private static final Logger logger = LoggerFactory.getLogger(CdnServiceImpl.class);

    @Value("${aliyun.cdn.privateKey}")
    private String              privateKey;

    @Override
    public String getAuthKey(String requestURI) {
        return getAuthKey("A", requestURI);
    }

    @Override
    public String getAuthKey(String type, String requestURI) {
        if (StringUtils.isBlank(type)) {
            return "";
        }

        if ("A".equals(type)) {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000 + 3600);
            String rand = UUID.randomUUID().toString().replace("-", "");
            String uid = "0";

            StringBuilder authKey = new StringBuilder();
            authKey.append(timestamp).append("-").append(rand).append("-").append(uid);

            String md5hash = null;

            try {
                md5hash = EncryptUtil
                    .encryptMd5(requestURI + "-" + authKey.toString() + "-" + privateKey);
            } catch (IOException e) {
                logger.error("encryptMD5", e);
            }

            return authKey.append("-").append(md5hash).toString();
        }

        return "";
    }
}
