package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.FileInfo;
import com.example.demo.dingtalk.api.bo.FileUploadInfo;

public interface StorageService {

    FileUploadInfo getFileUploadInfo(String accessToken, String spaceId,
                                     String unionId) throws RuntimeException;

    FileInfo commitFile(String accessToken, String spaceId, String unionId, String uploadKey,
                        String name, String parentId) throws RuntimeException;

}
