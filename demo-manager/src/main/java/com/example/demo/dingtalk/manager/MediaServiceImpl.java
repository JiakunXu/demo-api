package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.example.demo.dingtalk.api.MediaService;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("com.example.demo.dingtalk.manager.mediaService")
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Override
    public String upload(String accessToken, String type, String fileName, byte[] content) {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_UPLOAD_URL);

        OapiMediaUploadRequest request = new OapiMediaUploadRequest();
        request.setType(type);
        // 要上传的媒体文件
        request.setMedia(new FileItem(fileName, content));

        OapiMediaUploadResponse response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }

        return response.getMediaId();
    }

}
