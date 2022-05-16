package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.ShortLinkService;
import com.example.demo.weixin.api.bo.wxa.Link;
import com.example.demo.weixin.api.bo.wxa.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private static final Logger logger = LoggerFactory.getLogger(ShortLinkServiceImpl.class);

    @Override
    public Link generateShortLink(String accessToken, Page page) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (page == null) {
            throw new RuntimeException("page cannot be null.");
        }

        Link link;

        try {
            link = JSON.parseObject(HttpUtil.post(ShortLinkService.HTTPS_GENERATE_URL + accessToken,
                JSON.toJSONString(page)), Link.class);
        } catch (Exception e) {
            logger.error(page.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
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
