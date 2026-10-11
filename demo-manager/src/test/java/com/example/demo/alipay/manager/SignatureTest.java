package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.util.Signer;
import org.mockito.MockedStatic;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignatureTest {
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void verifiesQuotedContentWithConfiguredPublicKey(boolean certificate) throws Exception {
        Client kernel = mock(Client.class);
        com.alipay.easysdk.util.aes.Client aes = mock(com.alipay.easysdk.util.aes.Client.class);
        aes._kernel = kernel;
        when(kernel.isCertMode()).thenReturn(certificate);
        when(kernel.extractAlipayPublicKey("")).thenReturn("public-key");
        when(kernel.getConfig("alipayPublicKey")).thenReturn("public-key");
        try (MockedStatic<Factory.Util> factory = mockStatic(Factory.Util.class);
             MockedStatic<Signer> signer = mockStatic(Signer.class)) {
            factory.when(Factory.Util::AES).thenReturn(aes);
            signer.when(() -> Signer.verify("\"cipher\"", "signature", "public-key")).thenReturn(true);
            assertThat(Signature.rsaCheck("cipher", "signature")).isTrue();
            signer.verify(() -> Signer.verify("\"cipher\"", "signature", "public-key"));
            if (certificate) {
                verify(kernel).extractAlipayPublicKey("");
                verify(kernel, never()).getConfig(anyString());
            } else {
                verify(kernel).getConfig("alipayPublicKey");
                verify(kernel, never()).extractAlipayPublicKey(anyString());
            }
        }
    }
}
