package com.example.demo.aliyun.manager;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.demo.aliyun.api.GenerationService;
import com.example.demo.sse.api.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerationServiceImpl implements GenerationService {

    @Autowired
    private SseService sseService;

    @Override
    public void streamCall(String tunnelId, String model, String content) {
        List<Message> messages = new ArrayList<>();
        messages.add(Message.builder().role(Role.USER.getValue()).content(content).build());

        GenerationParam param = GenerationParam.builder().apiKey("").model(model).messages(messages)
            .resultFormat(GenerationParam.ResultFormat.MESSAGE).incrementalOutput(true).build();

        Generation generation = new Generation();

        try {
            generation.streamCall(param, new ResultCallback<>() {
                @Override
                public void onEvent(GenerationResult message) {
                    sseService.send(tunnelId, message.toString());
                }

                @Override
                public void onComplete() {
                    sseService.complete(tunnelId);
                }

                @Override
                public void onError(Exception e) {
                    sseService.complete(tunnelId);
                }
            });
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
