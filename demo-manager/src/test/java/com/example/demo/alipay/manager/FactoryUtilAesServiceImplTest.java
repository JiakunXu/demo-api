package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FactoryUtilAesServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void decrypt0BuildsRequestAndReturnsResult() throws Exception {
        String cipherText = "cipherText-value";
        FactoryUtilAesServiceImpl target = new FactoryUtilAesServiceImpl();
        com.alipay.easysdk.util.aes.Client client = mock(com.alipay.easysdk.util.aes.Client.class, RETURNS_SELF);
        String response = "converted-value";

        when(client.decrypt(cipherText)).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::AES).thenReturn(client);
            assertThat(target.decrypt(cipherText)).isEqualTo(response);
            verify(client).decrypt(cipherText);

        }
    }

    @Test
    void decrypt0PreservesSdkFailure() throws Exception {
        String cipherText = "cipherText-value";
        FactoryUtilAesServiceImpl target = new FactoryUtilAesServiceImpl();
        com.alipay.easysdk.util.aes.Client client = mock(com.alipay.easysdk.util.aes.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.decrypt(cipherText)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::AES).thenReturn(client);
            assertThatThrownBy(() -> target.decrypt(cipherText)).hasCause(failure);
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void encrypt1BuildsRequestAndReturnsResult() throws Exception {
        String plainText = "plainText-value";
        FactoryUtilAesServiceImpl target = new FactoryUtilAesServiceImpl();
        com.alipay.easysdk.util.aes.Client client = mock(com.alipay.easysdk.util.aes.Client.class, RETURNS_SELF);
        String response = "converted-value";

        when(client.encrypt(plainText)).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::AES).thenReturn(client);
            assertThat(target.encrypt(plainText)).isEqualTo(response);
            verify(client).encrypt(plainText);

        }
    }

    @Test
    void encrypt1PreservesSdkFailure() throws Exception {
        String plainText = "plainText-value";
        FactoryUtilAesServiceImpl target = new FactoryUtilAesServiceImpl();
        com.alipay.easysdk.util.aes.Client client = mock(com.alipay.easysdk.util.aes.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.encrypt(plainText)).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::AES).thenReturn(client);
            assertThatThrownBy(() -> target.encrypt(plainText)).hasCause(failure);
        }
    }
}
