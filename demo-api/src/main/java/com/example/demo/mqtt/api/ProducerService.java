package com.example.demo.mqtt.api;

public interface ProducerService {

    String sendMessage(String mqttTopic, byte[] payload);

}
