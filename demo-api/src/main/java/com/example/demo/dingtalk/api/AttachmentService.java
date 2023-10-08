package com.example.demo.dingtalk.api;

public interface AttachmentService {

    String getAttachment(String accessToken, String userId, String unionId, String fileList);

}
