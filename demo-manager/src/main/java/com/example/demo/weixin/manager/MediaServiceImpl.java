package com.example.demo.weixin.manager;

import com.example.demo.weixin.api.MediaService;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class MediaServiceImpl implements MediaService {

    @Override
    public byte[] getMedia(String accessToken, String mediaId) throws RuntimeException {
        try {
            return HttpUtil.download(MessageFormat.format(HTTPS_GET_URL, accessToken, mediaId));
        } catch (Exception e) {
            log.error("{},{}", accessToken, mediaId, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
