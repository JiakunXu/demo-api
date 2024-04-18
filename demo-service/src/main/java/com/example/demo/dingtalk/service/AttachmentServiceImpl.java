package com.example.demo.dingtalk.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.dingtalk.api.AttachmentService;
import com.example.demo.dingtalk.api.OssService;
import com.example.demo.dingtalk.api.StorageService;
import com.example.demo.dingtalk.api.WorkflowService;
import com.example.demo.dingtalk.api.bo.FileInfo;
import com.example.demo.dingtalk.api.bo.FileUploadInfo;
import com.example.demo.file.api.bo.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private OssService          ossService;

    @Autowired
    private StorageService      storageService;

    @Autowired
    private WorkflowService     workflowService;

    @Override
    public String getAttachment(String accessToken, String userId, String unionId,
                                String fileList) {
        if (StringUtils.isBlank(fileList)) {
            return null;
        }

        List<JSONObject> list = new ArrayList<>();

        Long spaceId = workflowService.getAttachmentSpace(accessToken, userId);

        List<File> files = JSON.parseArray(fileList, File.class);

        for (File file : files) {
            FileUploadInfo fileUploadInfo = storageService.getFileUploadInfo(accessToken,
                String.valueOf(spaceId), unionId);

            ossService.putObject(fileUploadInfo.getHeaderSignatureInfo().getResourceUrls().get(0),
                fileUploadInfo.getHeaderSignatureInfo().getHeaders(), file.getUrl());

            FileInfo fileInfo = storageService.commitFile(accessToken, String.valueOf(spaceId),
                unionId, fileUploadInfo.getUploadKey(), file.getName(), "0");

            JSONObject data = new JSONObject();
            data.put("spaceId", fileInfo.getSpaceId());
            data.put("fileName", fileInfo.getName());
            data.put("fileSize", fileInfo.getSize());
            data.put("fileType", fileInfo.getType());
            data.put("fileId", fileInfo.getId());

            list.add(data);
        }

        return JSON.toJSONString(list);
    }

}
