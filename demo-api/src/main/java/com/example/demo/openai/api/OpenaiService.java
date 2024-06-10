package com.example.demo.openai.api;

import com.example.demo.openai.api.bo.Message;

import java.util.List;

public interface OpenaiService {

    void chat(String tunnelId, List<Message> messages);

}
