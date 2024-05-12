package com.example.demo.aliyun.api;

/**
 * @author JiakunXu
 */
public interface ProducerService {

    String syncSend(String topic, String tags, byte[] body, String key);

    String syncSend(String topic, String tags, byte[] body, String key, long delayTime);

    void send(String topic, String tags, byte[] body, String key);

}
