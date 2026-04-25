package com.example.demo.mqtt.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.mqtt.server.callback.MessageListener;
import com.alibaba.mqtt.server.model.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ConsumerListener implements MessageListener {

    @Override
    public void process(String msgId, MessageProperties messageProperties, byte[] payload) {
        String content = new String(payload, StandardCharsets.UTF_8);
        log.info("Receive msgId={}, properties={}, payload={}", msgId,
            JSON.toJSONString(messageProperties), content);
    }

}
