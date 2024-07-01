package com.example.demo.baichuan.manager;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.baichuan.api.BaichuanService;
import com.example.demo.sse.api.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BaichuanServiceImpl implements BaichuanService {

    @Autowired
    private SseService sseService;

    @Override
    public void chat(String tunnelId) {
        JSONObject data = new JSONObject();
        data.put("model", "Baichuan4");
        data.put("messages", null);
        data.put("stream", true);
        data.put("temperature", 0.3);
        data.put("top_p", 0.85);
        data.put("top_k", 5);
        data.put("max_tokens", 2048);
        data.put("response_format", null);
        data.put("tools", null);
        data.put("tool_choice", null);

        WebClient client = WebClient.create("https://api.baichuan-ai.com");

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
