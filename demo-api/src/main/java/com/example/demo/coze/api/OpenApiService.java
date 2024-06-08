package com.example.demo.coze.api;

public interface OpenApiService {

    void chat(String tunnelId, String botId, String user, String query);

}
