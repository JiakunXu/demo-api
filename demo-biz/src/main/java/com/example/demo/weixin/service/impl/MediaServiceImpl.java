package com.example.demo.weixin.service.impl;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.api.weixin.MediaService;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Override
    public InputStream getMedia(String accessToken, String mediaId) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(mediaId)) {
            throw new RuntimeException("media_id cannot be null.");
        }

        try {
            return HttpUtil
                .download(MediaService.HTTPS_GET_URL.replace("$ACCESS_TOKEN$", accessToken.trim())
                    .replace("$MEDIA_ID$", mediaId.trim()));
        } catch (Exception e) {
            logger.error(accessToken + "&" + mediaId, e);

            throw new RuntimeException("HttpUtil error.", e);
        }
    }
}
