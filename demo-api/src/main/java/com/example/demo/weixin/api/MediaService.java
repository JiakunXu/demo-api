package com.example.demo.weixin.api;

/**
 * @author JiakunXu
 */
public interface MediaService {

    String HTTPS_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token={0}&media_id={1}";

    /**
     * 获取临时素材（即下载临时的多媒体文件）.
     * 
     * @param accessToken 调用接口凭证.
     * @param mediaId 媒体文件ID.
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/Get_temporary_materials.html">微信官方文档</a>
     */
    byte[] getMedia(String accessToken, String mediaId) throws RuntimeException;

}
