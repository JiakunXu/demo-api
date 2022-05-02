package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.WxaQrCodeService;
import com.example.demo.weixin.api.bo.wxa.WxaCode;
import com.example.demo.weixin.api.bo.wxa.WxaQrCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class WxaQrCodeServiceImpl implements WxaQrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(WxaQrCodeServiceImpl.class);

    @Override
    public WxaQrCode getWxaQrCode(String accessToken, WxaQrCode wxaQrCode) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (wxaQrCode == null) {
            throw new RuntimeException("wxa_qr_code cannot be null.");
        }

        byte[] buffer = null;

        try {
            buffer = HttpUtil.download(WxaQrCodeService.HTTPS_GET_URL + accessToken,
                JSON.toJSONString(wxaQrCode));
        } catch (Exception e) {
            logger.error(wxaQrCode.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        WxaCode code = JSON.parseObject(buffer, WxaCode.class);

        if (code != null) {
            throw new RuntimeException(code.getErrMsg());
        }

        wxaQrCode.setContentType("image/jpeg");
        wxaQrCode.setBuffer(buffer);

        return wxaQrCode;
    }

}
