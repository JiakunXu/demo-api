package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.QrCodeService;
import com.example.demo.bytedance.api.bo.BaseResult;
import com.example.demo.bytedance.api.bo.qrcode.Body;
import com.example.demo.framework.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.bytedance.manager.qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Override
    public byte[] create(String accessToken, Body body) throws RuntimeException {
        if (body == null) {
            throw new RuntimeException("body is null.");
        }

        body.setAccessToken(accessToken);

        byte[] qrCode;

        try {
            qrCode = HttpUtil.download(QrCodeService.HTTPS_POST_URL, JSON.toJSONString(body));
        } catch (Exception e) {
            logger.error(body.toString(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (qrCode == null) {
            throw new RuntimeException("qr_code is null.");
        }

        if (JSON.isValid(qrCode)) {
            BaseResult result = JSON.parseObject(qrCode, BaseResult.class);
            throw new RuntimeException(result.getErrMsg());
        }

        return qrCode;
    }

}
