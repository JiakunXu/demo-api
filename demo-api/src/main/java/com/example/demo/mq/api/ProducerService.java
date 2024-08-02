package com.example.demo.mq.api;

/**
 * @author JiakunXu
 */
public interface ProducerService {

    String syncSend(String topic, String tags, byte[] body, String keys);

    String syncSend(String topic, String tags, byte[] body, String keys, long delayTime);

    void send(String topic, String tags, byte[] body, String keys);

}
