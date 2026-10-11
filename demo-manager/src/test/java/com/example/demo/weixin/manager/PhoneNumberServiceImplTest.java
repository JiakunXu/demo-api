package com.example.demo.weixin.manager;


import org.junit.jupiter.api.Test;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import com.example.demo.weixin.api.bo.wxa.PhoneNumber;
import static com.example.demo.support.HttpContract.jsonBody;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class PhoneNumberServiceImplTest {
    private static final String KEY = "MDEyMzQ1Njc4OWFiY2RlZg==";
    private static final String IV = "YWJjZGVmMDEyMzQ1Njc4OQ==";
    // Fixed ciphertext generated independently with OpenSSL AES-128-CBC and PKCS#7 padding.
    private static final String ENCRYPTED = "n3MwVxGnRe37sf6ebzZjEJ94TOSIe0wHF0CkCRlM8x3TNbt2FctTBsUI458b+8aIvM2YGds8ArXlyS094uxMsdClvSBQoHcSdrZiXiM+PbEW3iBrxNOukbpj/+EJBszT+/dDBYAkmyPV96FnNHezNGVW4Jb6RoTv6ToJL66hYbA=";
    @Test
    void decryptsAndChecksAppWatermark() {
        var result = new PhoneNumberServiceImpl().getPhoneNumber("test-app", ENCRYPTED, KEY, IV);
        assertThat(result.getWatermark().getAppid()).isEqualTo("test-app");
        assertThat(result.getPhoneNumber()).isEqualTo("13800138000");
    }

    @Test
    void rejectsAnotherAppsEncryptedData() {
        assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber("other-app", ENCRYPTED, KEY, IV))
            .hasMessageContaining("not equals");
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(ints = { 0, 1, 2, 3 })
    void rejectsMissingEncryptedParameters(int index) {
        String[] args = { "test-app", ENCRYPTED, KEY, IV };
        args[index] = " ";
        assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber(args[0], args[1], args[2], args[3]))
            .hasMessageContaining(new String[] { "appid", "encryptedData", "sessionKey", "iv" }[index]);
    }

    @Test
    void rejectsNullDecryptedPayload() {
        assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber("test-app", "nYVsTQhxm8R5BHPqccR71Q==", KEY, IV))
            .hasMessage("phoneNumber is null.");
    }

    @TestFactory
    Stream<DynamicTest> phoneApi() {
        return HttpContract.json("phone code", () -> new Endpoint(
            () -> HttpUtil.post(eq("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=token"), jsonBody("{\"code\":\"code\"}")),
            () -> new PhoneNumberServiceImpl().getPhoneNumber("test-app", "token", "code"), PhoneNumber.class,
            "{\"phone_info\":{\"phoneNumber\":\"13800138000\",\"watermark\":{\"appid\":\"test-app\"}}}",
            "{\"phoneNumber\":\"13800138000\",\"watermark\":{\"appid\":\"test-app\"}}",
            "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null."));
    }

    @Test
    void phoneApiRejectsMismatchedWatermark() {
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.post(anyString(), anyString()))
                .thenReturn("{\"phone_info\":{\"watermark\":{\"appid\":\"other-app\"}}}");
            assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber("test-app", "token", "code"))
                .hasMessage("appid not equals.");
        }
    }

    @Test
    void phoneApiRejectsMissingPhoneInfo() {
        try (var http = mockStatic(HttpUtil.class)) {
            http.when(() -> HttpUtil.post(anyString(), anyString())).thenReturn("{}");
            assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber("test-app", "token", "code"))
                .hasMessage("phoneNumber is null.");
        }
    }
}
