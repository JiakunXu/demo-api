package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.WxaCodeService;
import com.example.demo.weixin.api.bo.wxa.WxaCode;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class WxaCodeServiceImpl implements WxaCodeService {

    private static final Logger logger = LoggerFactory.getLogger(WxaCodeServiceImpl.class);

    @Override
    public WxaCode getWxaCodeUnlimit(String accessToken, WxaCode wxaCode) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (wxaCode == null) {
            throw new RuntimeException("media_id cannot be null.");
        }

        byte[] buffer = null;

        try {
            buffer = HttpUtil.download(WxaCodeService.HTTPS_CODE_URL + accessToken,
                JSON.toJSONString(wxaCode));
        } catch (Exception e) {
            logger.error(wxaCode.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        WxaCode code = JSON.parseObject(buffer, WxaCode.class);

        if (code != null) {
            throw new RuntimeException(code.getErrMsg());
        }

        wxaCode.setContentType("image/jpeg");
        wxaCode.setBuffer(buffer);

        return wxaCode;
    }
}
