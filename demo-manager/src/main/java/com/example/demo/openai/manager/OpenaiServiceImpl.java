package com.example.demo.openai.manager;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.openai.api.OpenaiService;
import com.example.demo.openai.api.bo.Message;
import com.example.demo.sse.api.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service("com.example.demo.openai.manager.openaiService")
public class OpenaiServiceImpl implements OpenaiService {

    @Autowired
    private SseService sseService;

    @Override
    public void chat(String tunnelId, List<Message> messages) {
        JSONObject data = new JSONObject();
        data.put("messages", messages);
        data.put("model", "gpt-4");
        data.put("frequency_penalty", 0);
        data.put("logit_bias", null);
        data.put("logprobs", false);
        data.put("top_logprobs", null);
        data.put("max_tokens", null);
        data.put("n", 1);
        data.put("presence_penalty", 0);
        data.put("response_format", null);
        data.put("seed", null);
        data.put("stop", null);
        data.put("stream", true);
        data.put("stream_options", null);
        data.put("temperature", 1);
        data.put("top_p", 1);
        data.put("tools", null);
        data.put("tool_choice", null);
        data.put("parallel_tool_calls", true);
        data.put("user", null);

        WebClient client = WebClient.create("https://api.openai.com");

        client.post().uri("/v1/chat/completions").contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ").bodyValue(data.toString())
            .accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(String.class).map(s -> {
                sseService.send(tunnelId, s);
                return s;
            }).doFinally(s -> {
                sseService.complete(tunnelId);
            }).blockLast();
    }

}
