package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.QrCodeService;
import com.example.demo.weixin.api.bo.qrcode.QrCode;
import com.example.demo.weixin.api.bo.qrcode.Result;
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
    public Result create(String accessToken, QrCode qrCode) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (qrCode == null) {
            throw new RuntimeException("qrcode cannot be null.");
        }

        Result result = null;

        try {
            result = JSON.parseObject(HttpUtil.post(QrCodeService.HTTPS_CREATE_URL + accessToken,
                JSON.toJSONString(qrCode)), Result.class);
        } catch (Exception e) {
            logger.error(qrCode.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
