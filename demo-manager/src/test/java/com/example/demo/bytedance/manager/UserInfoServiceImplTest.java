package com.example.demo.bytedance.manager;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class UserInfoServiceImplTest {
    private static final String KEY = "MDEyMzQ1Njc4OWFiY2RlZg==";
    private static final String IV = "YWJjZGVmMDEyMzQ1Njc4OQ==";
    // Fixed ciphertext generated independently with OpenSSL AES-128-CBC and PKCS#7 padding.
    private static final String ENCRYPTED = "n3MwVxGnRe37sf6ebzZjEJ94TOSIe0wHF0CkCRlM8x3TNbt2FctTBsUI458b+8aIvM2YGds8ArXlyS094uxMsdClvSBQoHcSdrZiXiM+PbEW3iBrxNOukbpj/+EJBszT+/dDBYAkmyPV96FnNHezNGVW4Jb6RoTv6ToJL66hYbA=";
    @Test
    void decryptsAndChecksAppWatermark() {
        var result = new UserInfoServiceImpl().getUserInfo("test-app", ENCRYPTED, KEY, IV);
        assertThat(result.getWatermark().getAppid()).isEqualTo("test-app");
        assertThat(result.getNickName()).isEqualTo("测试用户");
    }

    @Test
    void rejectsAnotherAppsEncryptedData() {
        assertThatThrownBy(() -> new UserInfoServiceImpl().getUserInfo("other-app", ENCRYPTED, KEY, IV))
            .hasMessageContaining("not equals");
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(ints = { 0, 1, 2, 3 })
    void rejectsMissingEncryptedParameters(int index) {
        String[] args = { "test-app", ENCRYPTED, KEY, IV };
        args[index] = " ";
        assertThatThrownBy(() -> new UserInfoServiceImpl().getUserInfo(args[0], args[1], args[2], args[3]))
            .hasMessageContaining(new String[] { "appid", "encryptedData", "sessionKey", "iv" }[index]);
    }

    @Test
    void rejectsNullDecryptedPayload() {
        assertThatThrownBy(() -> new UserInfoServiceImpl().getUserInfo("test-app", "nYVsTQhxm8R5BHPqccR71Q==", KEY, IV))
            .hasMessage("userInfo is null.");
    }
}
