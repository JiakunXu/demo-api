package com.example.demo.mqtt.service;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.mqtt.server.callback.StatusListener;
import com.alibaba.mqtt.server.model.StatusNotice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientStatusListener implements StatusListener {

    @Override
    public void process(StatusNotice statusNotice) {
        System.out.println(JSONObject.toJSONString(statusNotice));
    }

}
