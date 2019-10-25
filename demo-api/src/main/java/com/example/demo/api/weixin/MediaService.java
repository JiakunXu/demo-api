package com.example.demo.api.weixin;

import java.io.InputStream;

/**
 * @author JiakunXu
 */
public interface MediaService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=$ACCESS_TOKEN$&media_id=$MEDIA_ID$";

    /**
     * 获取临时素材（即下载临时的多媒体文件）.
     * 
     * @param accessToken 调用接口凭证.
     * @param mediaId 媒体文件ID.
     * @return
     * @throws RuntimeException
     */
    InputStream getMedia(String accessToken, String mediaId) throws RuntimeException;

}
