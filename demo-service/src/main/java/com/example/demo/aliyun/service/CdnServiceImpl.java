package com.example.demo.aliyun.service;

import com.example.demo.aliyun.api.CdnService;
import com.example.demo.framework.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class CdnServiceImpl implements CdnService {

    @Value("${aliyun.cdn.privateKey}")
    private String privateKey;

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
                log.error("{},{}", type, requestURI, e);
            }

            return authKey.append("-").append(md5hash).toString();
        }

        return "";
    }
}
