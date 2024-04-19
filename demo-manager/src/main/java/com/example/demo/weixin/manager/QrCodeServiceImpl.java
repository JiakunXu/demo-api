package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.QrCodeService;
import com.example.demo.weixin.api.bo.qrcode.QrCode;
import com.example.demo.weixin.api.bo.qrcode.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeServiceImpl.class);

    @Override
    public Result create(String accessToken, QrCode qrCode) throws RuntimeException {
        Result result;

        try {
            result = JSON.parseObject(HttpUtil
                .post(MessageFormat.format(HTTPS_POST_URL, accessToken), JSON.toJSONString(qrCode)),
                Result.class);
        } catch (Exception e) {
            logger.error(qrCode.toString(), e);
            throw new RuntimeException(e);
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
