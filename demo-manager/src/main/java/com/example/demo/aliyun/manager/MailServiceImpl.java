package com.example.demo.aliyun.manager;

import com.aliyun.dm20151123.Client;
import com.aliyun.dm20151123.models.SingleSendMailRequest;
import com.aliyun.dm20151123.models.SingleSendMailResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.demo.aliyun.api.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${aliyun.accessKey.id}")
    private String accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String accessKeySecret;

    @Value("${aliyun.dm.endpoint}")
    private String endpoint;

    @Override
    public String singleSendMail(String accountName, String toAddress, String subject,
                                 String textBody) {
        Client client;

        try {
            client = new Client(new Config().setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret).setEndpoint(endpoint));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        SingleSendMailRequest request = new SingleSendMailRequest().setAccountName(accountName)
            .setAddressType(0).setReplyToAddress(false).setToAddress(toAddress).setSubject(subject)
            .setTextBody(textBody);

        SingleSendMailResponse response;

        try {
            response = client.singleSendMail(request);
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

        return response.getBody().getEnvId();
    }

}
