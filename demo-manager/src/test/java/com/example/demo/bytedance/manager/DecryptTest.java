package com.example.demo.bytedance.manager;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class DecryptTest {
    private static final String KEY = "MDEyMzQ1Njc4OWFiY2RlZg==";
    private static final String IV = "YWJjZGVmMDEyMzQ1Njc4OQ==";
    // Fixed ciphertext generated independently with OpenSSL AES-128-CBC and PKCS#7 padding.
    private static final String ENCRYPTED = "n3MwVxGnRe37sf6ebzZjEJ94TOSIe0wHF0CkCRlM8x3TNbt2FctTBsUI458b+8aIvM2YGds8ArXlyS094uxMsdClvSBQoHcSdrZiXiM+PbEW3iBrxNOukbpj/+EJBszT+/dDBYAkmyPV96FnNHezNGVW4Jb6RoTv6ToJL66hYbA=";
    @Test
    void decryptsIndependentUtf8Vector() throws Exception {
        assertThat(Decrypt.decrypt(ENCRYPTED, KEY, IV)).isEqualTo("{\"nickName\":\"测试用户\",\"phoneNumber\":\"13800138000\",\"countryCode\":\"86\",\"watermark\":{\"appid\":\"test-app\",\"timestamp\":1234}}");
    }

    @Test
    void rejectsInvalidBase64() {
        assertThatThrownBy(() -> Decrypt.decrypt("!", KEY, IV)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsInvalidKeyLength() {
        assertThatThrownBy(() -> Decrypt.decrypt(ENCRYPTED, "YQ==", IV))
            .isInstanceOf(java.security.InvalidKeyException.class);
    }
}
