package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.UrlSchemeService;
import com.example.demo.weixin.api.bo.wxa.OpenLink;
import com.example.demo.weixin.api.bo.wxa.UrlScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class UrlSchemeServiceImpl implements UrlSchemeService {

    private static final Logger logger = LoggerFactory.getLogger(UrlSchemeServiceImpl.class);

    @Override
    public OpenLink generateScheme(String accessToken,
                                   UrlScheme urlScheme) throws RuntimeException {
        OpenLink openLink;

        try {
            openLink = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                    JSON.toJSONString(urlScheme)), OpenLink.class);
        } catch (Exception e) {
            logger.error(urlScheme.toString(), e);
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
