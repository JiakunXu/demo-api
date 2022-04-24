package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.Link;
import com.example.demo.weixin.api.bo.wxa.Page;

/**
 * @author JiakunXu
 */
public interface ShortLinkService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/wxa/genwxashortlink?access_token=";

    /**
     * Short Link.
     * 
     * @param accessToken
     * @param page
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/short-link/shortlink.generate.html">微信官方文档</a>
     */
    Link generateShortLink(String accessToken, Page page) throws RuntimeException;

}
