package com.example.demo.dingtalk.manager;

import java.security.Security;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class DingCallbackCryptoTest {
    private static final String KEY = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY";
    @BeforeAll
    static void initializationPreservesJvmCryptoPolicy() throws Exception {
        String policy = Security.getProperty("crypto.policy");
        Class.forName(DingCallbackCrypto.class.getName());
        assertThat(Security.getProperty("crypto.policy")).isEqualTo(policy);
    }

    @Test
    void decryptsIndependentCallbackVector() throws Exception {
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "test-app");
        assertThat(crypto.getDecryptMsg("518da720f61cec74342dca9bc1a9c2f5e4f1a9be", "1234", "nonce", "fb2IJy+yWTdpKjuBAEdBdWMAtDCOBfYGjEHdN5qst5TqDYmAOWz0NpYmZMK0mdK0m8px5ubJZcUe8Gc6ycXy6w=="))
            .isEqualTo("{\"EventType\":\"check_url\"}");
    }

    @Test
    void rejectsTamperedSignature() throws Exception {
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "test-app");
        var error = catchThrowableOfType(DingCallbackCrypto.DingTalkEncryptException.class,
            () -> crypto.getDecryptMsg("tampered", "1234", "nonce", "fb2IJy+yWTdpKjuBAEdBdWMAtDCOBfYGjEHdN5qst5TqDYmAOWz0NpYmZMK0mdK0m8px5ubJZcUe8Gc6ycXy6w=="));
        assertThat(error.getCode()).isEqualTo(DingCallbackCrypto.DingTalkEncryptException.COMPUTE_SIGNATURE_ERROR);
    }

    @Test
    void rejectsWrongReceiver() throws Exception {
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "other-app");
        assertThatThrownBy(() -> crypto.getDecryptMsg("518da720f61cec74342dca9bc1a9c2f5e4f1a9be", "1234", "nonce", "fb2IJy+yWTdpKjuBAEdBdWMAtDCOBfYGjEHdN5qst5TqDYmAOWz0NpYmZMK0mdK0m8px5ubJZcUe8Gc6ycXy6w=="))
            .isInstanceOf(DingCallbackCrypto.DingTalkEncryptException.class);
    }

    @Test
    void encryptsWithExpectedMetadataAndVerifiableSignature() throws Exception {
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "test-app");
        var result = crypto.getEncryptedMap("success", 1234L, "nonce");
        assertThat(result).containsEntry("timeStamp", "1234").containsEntry("nonce", "nonce");
        String signature = crypto.getSignature("token", "1234", "nonce", result.get("encrypt"));
        assertThat(result.get("msg_signature")).isEqualTo(signature);
        assertThat(crypto.getDecryptMsg(signature, "1234", "nonce", result.get("encrypt"))).isEqualTo("success");
    }

    @Test
    void rejectsInvalidKeyAndMissingEncryptionParameters() throws Exception {
        assertThatThrownBy(() -> new DingCallbackCrypto("token", "bad", "app"))
            .isInstanceOf(DingCallbackCrypto.DingTalkEncryptException.class);
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "app");
        assertThatThrownBy(() -> crypto.getEncryptedMap(null, 1234L, "nonce"))
            .isInstanceOf(DingCallbackCrypto.DingTalkEncryptException.class);
        assertThatThrownBy(() -> crypto.getEncryptedMap("text", null, "nonce"))
            .isInstanceOf(DingCallbackCrypto.DingTalkEncryptException.class);
        assertThatThrownBy(() -> crypto.getEncryptedMap("text", 1234L, null))
            .isInstanceOf(DingCallbackCrypto.DingTalkEncryptException.class);
    }
    @Test
    void defaultAcknowledgmentHasValidSignatureAndCanBeDecrypted() throws Exception {
        DingCallbackCrypto crypto = new DingCallbackCrypto("token", KEY, "test-app");
        long before = System.currentTimeMillis();
        var result = crypto.getEncryptedMap("success");
        assertThat(result.get("nonce")).hasSize(16);
        assertThat(Long.parseLong(result.get("timeStamp"))).isBetween(before, System.currentTimeMillis());
        assertThat(crypto.getDecryptMsg(result.get("msg_signature"), result.get("timeStamp"), result.get("nonce"), result.get("encrypt")))
            .isEqualTo("success");
    }
}
