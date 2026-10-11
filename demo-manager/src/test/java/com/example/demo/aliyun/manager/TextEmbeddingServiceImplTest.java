package com.example.demo.aliyun.manager;

import com.alibaba.dashscope.embeddings.*;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.mockito.MockedConstruction;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TextEmbeddingServiceImplTest {
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void sendsSingleOrMultipleTexts(boolean batch) throws Exception {
        try (MockedConstruction<TextEmbedding> clients = mockConstruction(TextEmbedding.class)) {
            var service = new TextEmbeddingServiceImpl();
            if (batch) service.call("model", List.of("hello", "world"));
            else service.call("model", "hello");
            var param = ArgumentCaptor.forClass(TextEmbeddingParam.class);
            verify(clients.constructed().getFirst()).call(param.capture(), any(ResultCallback.class));
            assertThat(param.getValue().getModel()).isEqualTo("model");
            assertThat(com.alibaba.fastjson2.JSON.parseArray(param.getValue().getInput().getAsJsonArray("texts").toString(), String.class)).containsExactlyElementsOf(batch ? List.of("hello", "world") : List.of("hello"));
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void wrapsSdkFailureForBothOverloads(boolean batch) throws Exception {
        var failure = new NoApiKeyException();
        try (MockedConstruction<TextEmbedding> clients = mockConstruction(TextEmbedding.class, (client, context) -> doThrow(failure).when(client).call(any(TextEmbeddingParam.class), any(ResultCallback.class)))) {
            assertThatThrownBy(() -> {
                if (batch) new TextEmbeddingServiceImpl().call("model", List.of("hello"));
                else new TextEmbeddingServiceImpl().call("model", "hello");
            }).hasCause(failure);
        }
    }
}
