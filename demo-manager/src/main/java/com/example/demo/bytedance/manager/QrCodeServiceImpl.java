package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.example.demo.bytedance.api.QrCodeService;
import com.example.demo.bytedance.api.bo.BaseResult;
import com.example.demo.bytedance.api.bo.qrcode.Body;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Override
    public byte[] create(String accessToken, Body body) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (body == null) {
            throw new RuntimeException("body is null.");
        }

        body.setAccessToken(accessToken);

        byte[] qrCode = null;

        try {
            qrCode = HttpUtil.download(QrCodeService.HTTPS_CREATE_URL, JSON.toJSONString(body));
        } catch (Exception e) {
            logger.error(body.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (qrCode == null) {
            throw new RuntimeException("qr_code is null.");
        }

        if (JSONValidator.fromUtf8(qrCode).validate()) {
            BaseResult result = JSON.parseObject(qrCode, BaseResult.class);

            throw new RuntimeException(result.getErrMsg());
        }

        return qrCode;
    }

}
