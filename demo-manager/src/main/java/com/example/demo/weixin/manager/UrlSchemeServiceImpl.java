package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UrlSchemeService;
import com.example.demo.weixin.api.bo.wxa.OpenLink;
import com.example.demo.weixin.api.bo.wxa.UrlScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class UrlSchemeServiceImpl implements UrlSchemeService {

    @Override
    public OpenLink generateScheme(String accessToken,
                                   UrlScheme urlScheme) throws RuntimeException {
        OpenLink openLink;

        try {
            openLink = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                    JSON.toJSONString(urlScheme)), OpenLink.class);
        } catch (Exception e) {
            log.error("{}", urlScheme, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (openLink == null) {
            throw new RuntimeException("open_link is null.");
        }

        if (openLink.getErrCode() != 0) {
            throw new RuntimeException(openLink.getErrMsg());
        }

        return openLink;
    }

}
