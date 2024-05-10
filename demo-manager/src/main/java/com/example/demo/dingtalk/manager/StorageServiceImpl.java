package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dingtalkstorage_1_0.Client;
import com.aliyun.dingtalkstorage_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.demo.dingtalk.api.StorageService;
import com.example.demo.dingtalk.api.bo.FileInfo;
import com.example.demo.dingtalk.api.bo.FileUploadInfo;
import com.example.demo.framework.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Override
    public FileUploadInfo getFileUploadInfo(String accessToken, String spaceId,
                                            String unionId) throws RuntimeException {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GetFileUploadInfoHeaders headers = new GetFileUploadInfoHeaders()
            .setXAcsDingtalkAccessToken(accessToken);

        GetFileUploadInfoRequest request = new GetFileUploadInfoRequest().setUnionId(unionId)
            .setProtocol("HEADER_SIGNATURE").setMultipart(false);

        GetFileUploadInfoResponse response;

        try {
            response = client.getFileUploadInfoWithOptions(spaceId, request, headers,
                new RuntimeOptions());
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e);
            }
        }

        return BeanUtil.copy(response.getBody(), FileUploadInfo.class);
    }

    @Override
    public FileInfo commitFile(String accessToken, String spaceId, String unionId, String uploadKey,
                               String name, String parentId) throws RuntimeException {
        Client client;

        try {
            client = new Client(new Config().setProtocol("https").setRegionId("central"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CommitFileHeaders commitFileHeaders = new CommitFileHeaders()
            .setXAcsDingtalkAccessToken(accessToken);

        CommitFileRequest request = new CommitFileRequest().setUnionId(unionId)
            .setUploadKey(uploadKey).setName(name).setParentId(parentId);

        CommitFileResponse response;

        try {
            response = client.commitFileWithOptions(spaceId, request, commitFileHeaders,
                new RuntimeOptions());
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e);
            }
        }

        return BeanUtil.copy(response.getBody().getDentry(), FileInfo.class);
    }

}
