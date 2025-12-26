package com.example.demo.aliyun.manager;

import com.aliyun.dyvmsapi20170525.Client;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsRequest;
import com.aliyun.dyvmsapi20170525.models.SingleCallByTtsResponse;
import com.aliyun.dyvmsapi20170525.models.SingleCallByVoiceRequest;
import com.aliyun.dyvmsapi20170525.models.SingleCallByVoiceResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.demo.aliyun.api.VmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VmsServiceImpl implements VmsService {

    @Value("${aliyun.accessKey.id}")
    private String accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String accessKeySecret;

    @Override
    public String singleCallByVoice(String calledNumber, String voiceCode) {
        Client client;

        try {
            client = new Client(new Config().setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret).setEndpoint("dyvmsapi.aliyuncs.com"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        SingleCallByVoiceRequest request = new SingleCallByVoiceRequest()
            .setCalledNumber(calledNumber).setVoiceCode(voiceCode);

        SingleCallByVoiceResponse response;

        try {
            response = client.singleCallByVoice(request);
        } catch (Exception e) {
            log.error("{}", request, e);
            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (!"OK".equals(response.getBody().getCode())) {
            throw new RuntimeException(response.getBody().getMessage());
        }

        return response.getBody().getCallId();
    }

    @Override
    public String singleCallByTts(String calledNumber, String ttsCode, String ttsParam) {
        Client client;

        try {
            client = new Client(new Config().setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret).setEndpoint("dyvmsapi.aliyuncs.com"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        SingleCallByTtsRequest request = new SingleCallByTtsRequest().setCalledNumber(calledNumber)
            .setTtsCode(ttsCode).setTtsParam(ttsParam);

        SingleCallByTtsResponse response;

        try {
            response = client.singleCallByTts(request);
        } catch (Exception e) {
            log.error("{}", request, e);
            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        if (!"OK".equals(response.getBody().getCode())) {
            throw new RuntimeException(response.getBody().getMessage());
        }

        return response.getBody().getCallId();
    }

}
