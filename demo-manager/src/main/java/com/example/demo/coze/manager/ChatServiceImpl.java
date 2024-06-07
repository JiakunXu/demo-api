package com.example.demo.coze.manager;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.coze.api.ChatService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("com.example.demo.coze.manager.chatService")
public class ChatServiceImpl implements ChatService {

    @Override
    public void chat(String botId, String user, String query) {
        JSONObject data = new JSONObject();
        data.put("bot_id", botId);
        data.put("conversation_id", "");
        data.put("user", user);
        data.put("query", query);
        data.put("stream", true);

        WebClient client = WebClient.create("https://api.coze.com");

        client.post().uri("/open_api/v2/chat").contentType(MediaType.APPLICATION_JSON)
            .header("Authorization",
                "Bearer pat_UExbmB3Sbb0za1zqE8n8v8YojTtpkCBxpVesHMrvlfgPnDGMImGnhbDX0Z1zJF46")
            .bodyValue(data.toString()).accept(MediaType.TEXT_EVENT_STREAM).retrieve()
            .bodyToFlux(String.class).map(s -> {
                System.out.println(s);
                return s;
            }).doFinally(s -> {

            }).blockLast();
    }

}
