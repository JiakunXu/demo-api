package com.example.demo.aliyun.manager;

import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.embeddings.TextEmbedding;
import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import com.alibaba.dashscope.embeddings.TextEmbeddingResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.demo.aliyun.api.TextEmbeddingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextEmbeddingServiceImpl implements TextEmbeddingService {

    @Override
    public void call(String model, String text) {
        TextEmbeddingParam param = TextEmbeddingParam.builder().apiKey("").model(model).text(text)
            .build();

        TextEmbedding textEmbedding = new TextEmbedding();

        try {
            textEmbedding.call(param, new ResultCallback<>() {
                @Override
                public void onEvent(TextEmbeddingResult message) {
                    System.out.println(message);
                }

                @Override
                public void onComplete() {
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (ApiException | NoApiKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void call(String model, List<String> texts) {
        TextEmbeddingParam param = TextEmbeddingParam.builder().apiKey("").model(model).texts(texts)
            .build();

        TextEmbedding textEmbedding = new TextEmbedding();

        try {
            textEmbedding.call(param, new ResultCallback<>() {
                @Override
                public void onEvent(TextEmbeddingResult message) {
                    System.out.println(message);
                }

                @Override
                public void onComplete() {
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (ApiException | NoApiKeyException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
