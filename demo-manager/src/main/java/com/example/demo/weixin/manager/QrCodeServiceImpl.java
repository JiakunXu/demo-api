package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.QrCodeService;
import com.example.demo.weixin.api.bo.qrcode.QrCode;
import com.example.demo.weixin.api.bo.qrcode.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Override
    public Result create(String accessToken, QrCode qrCode) throws RuntimeException {
        Result result;

        try {
            result = JSON.parseObject(HttpUtil
                .post(MessageFormat.format(HTTPS_POST_URL, accessToken), JSON.toJSONString(qrCode)),
                Result.class);
        } catch (Exception e) {
            log.error("{}", qrCode, e);
            throw new RuntimeException(e.getMessage(), e);
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
