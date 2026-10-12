package com.example.demo.mqtt.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.mqtt.server.model.StatusNotice;
import org.junit.jupiter.api.parallel.ResourceLock;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientStatusListenerTest {
    @Test
    @ResourceLock("SYSTEM_OUT")
    void outputsStatusNoticeAsReadableJson() {
        StatusNotice notice = mock(StatusNotice.class); when(notice.getClientId()).thenReturn("client");
        PrintStream previous = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            new ClientStatusListener().process(notice);
        } finally { System.setOut(previous); }
        assertThat(JSON.parseObject(bytes.toString(StandardCharsets.UTF_8).trim()).getString("clientId")).isEqualTo("client");
    }
}
