package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.WxaCodeService;
import com.example.demo.weixin.api.bo.BaseResult;
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
    public byte[] getWxaCodeUnlimit(String accessToken, WxaCode wxaCode) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (wxaCode == null) {
            throw new RuntimeException("wxa_code cannot be null.");
        }

        byte[] buffer;

        try {
            buffer = HttpUtil.download(WxaCodeService.HTTPS_GET_URL + accessToken,
                JSON.toJSONString(wxaCode));
        } catch (Exception e) {
            logger.error(wxaCode.toString(), e);

            throw new RuntimeException(e);
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
