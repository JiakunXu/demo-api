package com.example.demo.dingtalk.api;

public interface MediaService {

    String HTTPS_UPLOAD_URL = "https://oapi.dingtalk.com/media/upload";

    String upload(String accessToken, String type, String fileName, byte[] content);

}
