package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.bytedance.api.QrCodeService;
import com.example.demo.bytedance.api.bo.qrcode.Body;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Override
    public void create(String accessToken, Body body) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (body == null) {
            throw new RuntimeException("body is null.");
        }

        body.setAccessToken(accessToken);

        try {
            String s = HttpUtil.post(QrCodeService.HTTPS_CREATE_URL, JSON.toJSONString(body));
            System.out.print(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
