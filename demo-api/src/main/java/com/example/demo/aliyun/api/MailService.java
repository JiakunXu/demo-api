package com.example.demo.aliyun.api;

public interface MailService {

    String singleSendMail(String accountName, String toAddress, String subject, String textBody);

}
