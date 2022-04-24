package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.wxa.OpenLink;
import com.example.demo.weixin.api.bo.wxa.UrlScheme;

/**
 * @author JiakunXu
 */
public interface UrlSchemeService {

    String HTTPS_GENERATE_URL = "https://api.weixin.qq.com/wxa/generatescheme?access_token=";

    /**
     * URL Scheme.
     *
     * @param accessToken
     * @param urlScheme
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/url-scheme/urlscheme.generate.html">微信官方文档</a>
     */
    OpenLink generateScheme(String accessToken, UrlScheme urlScheme) throws RuntimeException;

}
