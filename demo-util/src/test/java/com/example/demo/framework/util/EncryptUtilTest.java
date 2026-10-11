package com.example.demo.framework.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class EncryptUtilTest {
    @ParameterizedTest
    @CsvSource({"'', d41d8cd98f00b204e9800998ecf8427e", "abc, 900150983cd24fb0d6963f7d28e17f72", "hello, 5d41402abc4b2a76b9719d911017c592"})
    void md5MatchesKnownVectors(String input, String expected) throws Exception {
        assertThat(EncryptUtil.encryptMd5(input)).isEqualTo(expected);
    }

    @Test
    void shaMethodUsesSha512KnownVector() throws Exception {
        assertThat(EncryptUtil.encryptSha("abc")).isEqualTo(
            "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a"
            + "2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f");
    }

    @Test
    void hmacMatchesRfc4231TestCaseTwo() throws Exception {
        assertThat(EncryptUtil.encryptHmac("what do ya want for nothing?", "Jefe"))
            .isEqualTo("5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843");
        assertThat(EncryptUtil.encryptHmac("what do ya want for nothing!", "Jefe"))
            .isNotEqualTo(EncryptUtil.encryptHmac("what do ya want for nothing?", "Jefe"));
    }

    @Test
    void unicodeUsesUtf8AndLowercaseHex() throws Exception {
        assertThat(EncryptUtil.encryptMd5("你好")).isEqualTo("7eca689f0d3389d9dea66ae112e5cfd7");
        assertThat(EncryptUtil.encryptHmac("消息", "密钥")).matches("[0-9a-f]{64}");
    }

    @Test
    void nullInputAndEmptyKeyAreRejected() {
        assertThatThrownBy(() -> EncryptUtil.encryptMd5(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> EncryptUtil.encryptSha(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> EncryptUtil.encryptHmac(null, "key")).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> EncryptUtil.encryptHmac("data", null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> EncryptUtil.encryptHmac("data", "")).isInstanceOf(IllegalArgumentException.class);
    }
}
