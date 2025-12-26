package com.example.demo.aliyun.api;

public interface VmsService {

    String singleCallByVoice(String calledNumber, String voiceCode);

    String singleCallByTts(String calledNumber, String ttsCode, String ttsParam);

}
