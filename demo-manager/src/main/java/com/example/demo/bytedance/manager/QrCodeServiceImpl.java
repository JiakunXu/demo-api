package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.QrCodeService;
import com.example.demo.bytedance.api.bo.BaseResult;
import com.example.demo.bytedance.api.bo.qrcode.Body;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Slf4j
@Service("com.example.demo.bytedance.manager.qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {

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
            log.error("{}", body, e);
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
