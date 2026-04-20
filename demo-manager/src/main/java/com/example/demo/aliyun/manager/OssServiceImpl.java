package com.example.demo.aliyun.manager;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.demo.aliyun.api.OssService;
import com.example.demo.framework.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.accessKey.id}")
    private String accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String secretAccessKey;

    @Override
    public String putObject(String bucketName, String key, byte[] content, String contentType) {
        return putObject(bucketName, key, new ByteArrayInputStream(content), contentType);
    }

    @Override
    public String putObject(String bucketName, String key, InputStream content,
                            String contentType) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, content);

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                      + "but was rejected with an error response for some reason.");
            log.error("Error Message: " + oe.getErrorMessage());
            log.error("Error Code: " + oe.getErrorCode());
            log.error("Request ID: " + oe.getRequestId());
            log.error("Host ID: " + oe.getHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                      + "a serious internal problem while trying to communicate with OSS, "
                      + "such as not being able to access the network.");
            log.error("Error Message: " + ce.getMessage());

            throw new RuntimeException(ce.getMessage(), ce);
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return key;
    }

    @Override
    public String copyObject(String sourceBucketName, String sourceKey,
                             String destinationBucketName, String destinationKey,
                             String contentType) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

        try {
            // 创建CopyObjectRequest对象。
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName, sourceKey,
                destinationBucketName, destinationKey);

            // 设置新的文件元信息。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            copyObjectRequest.setNewObjectMetadata(metadata);

            // 复制文件。
            ossClient.copyObject(copyObjectRequest);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                      + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                      + "a serious internal problem while trying to communicate with OSS, "
                      + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());

            throw new RuntimeException(ce.getMessage(), ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return destinationKey;
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, String contentType) {
        ClientConfiguration clientConfiguration = new ClientBuilderConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTPS);
        clientConfiguration.setSignatureVersion(SignVersion.V4);

        OSS ossClient = OSSClientBuilder.create().endpoint(endpoint)
            .credentialsProvider(new DefaultCredentialProvider(accessKeyId, secretAccessKey))
            .clientConfiguration(clientConfiguration).region("cn-hangzhou").build();

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key,
            HttpMethod.PUT);
        request.setExpiration(DateUtil.addMinutes(new Date(), 10));
        request.setHeaders(Collections.singletonMap(OSSHeaders.CONTENT_TYPE, contentType));

        try {
            return ossClient.generatePresignedUrl(request);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                      + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                      + "a serious internal problem while trying to communicate with OSS, "
                      + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());

            throw new RuntimeException(ce.getMessage(), ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) {
        return generatePresignedUrl(bucketName, key, expiration, null);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration,
                                    String process) {
        ClientConfiguration clientConfiguration = new ClientBuilderConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTPS);
        clientConfiguration.setSignatureVersion(SignVersion.V4);

        OSS ossClient = OSSClientBuilder.create().endpoint(endpoint)
            .credentialsProvider(new DefaultCredentialProvider(accessKeyId, secretAccessKey))
            .clientConfiguration(clientConfiguration).region("cn-hangzhou").build();

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
        request.setExpiration(expiration);
        request.setProcess(process);

        try {
            return ossClient.generatePresignedUrl(request);
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                      + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                      + "a serious internal problem while trying to communicate with OSS, "
                      + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());

            throw new RuntimeException(ce.getMessage(), ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
