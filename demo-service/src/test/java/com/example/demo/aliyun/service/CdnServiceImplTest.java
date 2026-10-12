package com.example.demo.aliyun.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CdnServiceImplTest {
    private final CdnServiceImpl service = new CdnServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "privateKey", "secret"); }
    @Test
    void defaultTypeCreatesOneHourSignatureWithIndependentMd5() throws Exception {
        long before = System.currentTimeMillis() / 1000;
        String value = service.getAuthKey("/image.png");
        String[] pieces = value.split("-");
        assertThat(pieces).hasSize(4);
        assertThat(pieces[1]).matches("[0-9a-f]{32}");
        assertThat(pieces[2]).isEqualTo("0");
        assertThat(Long.parseLong(pieces[0])).isBetween(before + 3600, System.currentTimeMillis() / 1000 + 3600);
        String plain = "/image.png-" + pieces[0] + "-" + pieces[1] + "-0-secret";
        assertThat(pieces[3]).isEqualTo(HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(plain.getBytes(StandardCharsets.UTF_8))));
    }

    @Test
    void unsupportedOrBlankTypeHasNoAuthKey() {
        assertThat(service.getAuthKey(null, "/file")).isEmpty();
        assertThat(service.getAuthKey(" ", "/file")).isEmpty();
        assertThat(service.getAuthKey("B", "/file")).isEmpty();
        assertThat(service.getAuthKey("A", "/file")).isNotBlank();
    }
}
