package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.ShortLinkService;
import com.example.demo.weixin.api.bo.wxa.Link;
import com.example.demo.weixin.api.bo.wxa.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    @Override
    public Link generateShortLink(String accessToken, Page page) throws RuntimeException {
        Link link;

        try {
            link = JSON.parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                JSON.toJSONString(page)), Link.class);
        } catch (Exception e) {
            log.error("{}", page, e);
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
