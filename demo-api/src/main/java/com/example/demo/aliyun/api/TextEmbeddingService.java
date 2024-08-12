package com.example.demo.aliyun.api;

import java.util.List;

public interface TextEmbeddingService {

    void call(String model, String text);

    void call(String model, List<String> texts);

}
