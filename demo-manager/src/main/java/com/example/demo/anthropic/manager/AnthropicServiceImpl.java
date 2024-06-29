package com.example.demo.anthropic.manager;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.anthropic.api.AnthropicService;
import com.example.demo.sse.api.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AnthropicServiceImpl implements AnthropicService {

    @Autowired
    private SseService sseService;

    @Override
    public void messages(String tunnelId) {
        JSONObject data = new JSONObject();
        data.put("model", "claude-3-5-sonnet-20240620");
        data.put("messages", null);
        data.put("max_tokens", 1024);
        data.put("metadata", null);
        data.put("stop_sequences", null);
        data.put("stream", true);
        data.put("system", null);
        data.put("temperature", 1);
        data.put("tool_choice", null);
        data.put("tools", null);
        data.put("top_k", null);
        data.put("top_p", null);

        WebClient client = WebClient.create("https://api.anthropic.com");

        client.post().uri("/v1/messages").contentType(MediaType.APPLICATION_JSON)
            .header("x-api-key", "").header("anthropic-version", "2023-06-01")
            .bodyValue(data.toString()).accept(MediaType.TEXT_EVENT_STREAM).retrieve()
            .bodyToFlux(String.class).map(s -> {
                sseService.send(tunnelId, s);
                return s;
            }).doFinally(s -> {
                sseService.complete(tunnelId);
            }).blockLast();
    }

}
