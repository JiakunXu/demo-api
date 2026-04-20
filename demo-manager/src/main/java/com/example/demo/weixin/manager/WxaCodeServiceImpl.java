package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.WxaCodeService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.WxaCode;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class WxaCodeServiceImpl implements WxaCodeService {

    @Override
    public byte[] getWxaCodeUnlimit(String accessToken, WxaCode wxaCode) throws RuntimeException {
        byte[] buffer;

        try {
            buffer = HttpUtil.download(MessageFormat.format(HTTPS_GET_URL, accessToken),
                JSON.toJSONString(wxaCode));
        } catch (Exception e) {
            log.error("{}", wxaCode, e);
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
