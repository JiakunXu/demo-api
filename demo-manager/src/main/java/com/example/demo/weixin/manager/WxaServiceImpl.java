package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.WxaService;
import com.example.demo.weixin.api.bo.WxaCode;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class WxaServiceImpl implements WxaService {

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
            buffer = HttpUtil.download(
                WxaService.HTTPS_CODE_URL.replace("$ACCESS_TOKEN$", accessToken.trim()),
                JSON.toJSONString(wxaCode));
        } catch (Exception e) {
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
