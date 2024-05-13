package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.ShortLinkService;
import com.example.demo.weixin.api.bo.wxa.Link;
import com.example.demo.weixin.api.bo.wxa.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private static final Logger logger = LoggerFactory.getLogger(ShortLinkServiceImpl.class);

    @Override
    public Link generateShortLink(String accessToken, Page page) throws RuntimeException {
        Link link;

        try {
            link = JSON.parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                JSON.toJSONString(page)), Link.class);
        } catch (Exception e) {
            logger.error(page.toString(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (link == null) {
            throw new RuntimeException("link is null.");
        }

        if (link.getErrCode() != 0) {
            throw new RuntimeException(link.getErrMsg());
        }

        return link;
    }

}
