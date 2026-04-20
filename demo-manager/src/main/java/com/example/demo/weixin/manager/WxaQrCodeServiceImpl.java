package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.WxaQrCodeService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.WxaQrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class WxaQrCodeServiceImpl implements WxaQrCodeService {

    @Override
    public byte[] getWxaQrCode(String accessToken, WxaQrCode wxaQrCode) throws RuntimeException {
        byte[] buffer;

        try {
            buffer = HttpUtil.download(MessageFormat.format(HTTPS_GET_URL, accessToken),
                JSON.toJSONString(wxaQrCode));
        } catch (Exception e) {
            log.error("{}", wxaQrCode, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (JSON.isValidObject(buffer)) {
            BaseResult result = JSON.parseObject(buffer, BaseResult.class);

            if (result != null) {
                throw new RuntimeException(result.getErrMsg());
            }
        }

        return buffer;
    }

}
