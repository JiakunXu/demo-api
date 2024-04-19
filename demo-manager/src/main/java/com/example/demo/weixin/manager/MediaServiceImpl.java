package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.MediaService;
import com.example.demo.framework.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Override
    public byte[] getMedia(String accessToken, String mediaId) throws RuntimeException {
        try {
            return HttpUtil.download(MessageFormat.format(HTTPS_GET_URL, accessToken, mediaId));
        } catch (Exception e) {
            logger.error(accessToken + "&" + mediaId, e);

            throw new RuntimeException(e);
        }
    }

}
