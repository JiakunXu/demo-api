package com.example.demo.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.example.demo.framework.util.HttpUtil;
import org.junit.jupiter.api.DynamicTest;
import org.mockito.MockedStatic;
import java.util.function.Supplier;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mockStatic;

/**
 * Shared scenarios for JSON HTTP adapters. Each scenario gets a fresh service and request DTO.
 * Static mocks are closed after each scenario to keep provider tests isolated.
 */
public final class HttpContract {
    private HttpContract() { }
    /** Matches required request fields while allowing DTO default fields in the serialized JSON. */
    public static String jsonBody(String expected) {
        return argThat(actual -> actual != null && containsJson(JSON.parse(actual), JSON.parse(expected)));
    }

    private static boolean containsJson(Object actual, Object expected) {
        if (expected instanceof java.util.Map<?, ?> entries && actual instanceof java.util.Map<?, ?> values) {
            return entries.entrySet().stream().allMatch(e -> values.containsKey(e.getKey())
                && containsJson(values.get(e.getKey()), e.getValue()));
        }
        if (expected instanceof java.util.List<?> entries && actual instanceof java.util.List<?> values) {
            if (entries.size() != values.size()) { return false; }
            for (int i = 0; i < entries.size(); i++) {
                if (!containsJson(values.get(i), entries.get(i))) { return false; }
            }
            return true;
        }
        return java.util.Objects.equals(actual, expected);
    }

    public static Stream<DynamicTest> json(String name, Supplier<Endpoint> factory) {
        return Stream.of("success", "business error", "empty", "malformed", "transport error")
            .map(branch -> DynamicTest.dynamicTest(name + " / " + branch, () -> {
                Endpoint e = factory.get();
                try (var http = mockStatic(HttpUtil.class)) {
                    if (branch.equals("transport error")) {
                        RuntimeException failure = new RuntimeException("simulated connection failure");
                        http.when(e.request()).thenThrow(failure);
                        assertThatThrownBy(e.call()::get).hasCause(failure);
                    } else {
                        String response = switch (branch) {
                            case "success" -> e.success();
                            case "business error" -> e.error();
                            case "malformed" -> "not-json";
                            default -> "null";
                        };
                        http.when(e.request()).thenReturn(response);
                        if (branch.equals("success")) {
                            assertThat(e.call().get()).usingRecursiveComparison()
                                .isEqualTo(JSON.parseObject(e.expected(), e.type()));
                        } else if (branch.equals("malformed")) {
                            assertThatThrownBy(e.call()::get).hasCauseInstanceOf(JSONException.class);
                        } else {
                            assertThatThrownBy(e.call()::get).hasMessage(branch.equals("empty")
                                ? e.emptyMessage() : "provider rejected");
                        }
                    }
                    http.verify(e.request());
                    http.verifyNoMoreInteractions();
                }
            }));
    }

    public record Endpoint(MockedStatic.Verification request, Supplier<?> call, Class<?> type,
                           String success, String expected, String error, String emptyMessage) {
        public Endpoint(MockedStatic.Verification request, Supplier<?> call, Class<?> type,
                        String success, String error, String emptyMessage) {
            this(request, call, type, success, success, error, emptyMessage);
        }
    }
}
