package com.example.demo.alipay.manager;

import com.example.demo.alipay.api.FactoryUtilAesService;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneNumberServiceImplTest {
    @Test
    void decryptsVerifiedPhone() throws Exception {
        FactoryUtilAesService aes = mock(FactoryUtilAesService.class);
        PhoneNumberServiceImpl service = new PhoneNumberServiceImpl();
        ReflectionTestUtils.setField(service, "factoryUtilAesService", aes);
        when(aes.decrypt("cipher")).thenReturn("{\"code\":\"10000\",\"mobile\":\"13800138000\"}");
        try (MockedStatic<Signature> signature = mockStatic(Signature.class)) {
            signature.when(() -> Signature.rsaCheck("cipher", "signature")).thenReturn(true);
            assertThat(service.getPhoneNumber("cipher", "signature").getMobile()).isEqualTo("13800138000");
            signature.verify(() -> Signature.rsaCheck("cipher", "signature"));
            verify(aes).decrypt("cipher");
        }
    }

    @Test
    void invalidSignatureDoesNotDecrypt() throws Exception {
        FactoryUtilAesService aes = mock(FactoryUtilAesService.class);
        PhoneNumberServiceImpl service = new PhoneNumberServiceImpl();
        ReflectionTestUtils.setField(service, "factoryUtilAesService", aes);
        try (MockedStatic<Signature> signature = mockStatic(Signature.class)) {
            signature.when(() -> Signature.rsaCheck("cipher", "signature")).thenReturn(false);
            assertThatThrownBy(() -> service.getPhoneNumber("cipher", "signature")).hasMessageContaining("验签失败");
            verifyNoInteractions(aes);
        }
    }

    @Test
    void rejectsBusinessErrorAfterVerification() throws Exception {
        FactoryUtilAesService aes = mock(FactoryUtilAesService.class);
        PhoneNumberServiceImpl service = new PhoneNumberServiceImpl();
        ReflectionTestUtils.setField(service, "factoryUtilAesService", aes);
        when(aes.decrypt("cipher")).thenReturn("{\"code\":\"40004\",\"msg\":\"denied\",\"subMsg\":\"expired\"}");
        try (MockedStatic<Signature> signature = mockStatic(Signature.class)) {
            signature.when(() -> Signature.rsaCheck("cipher", "signature")).thenReturn(true);
            assertThatThrownBy(() -> service.getPhoneNumber("cipher", "signature")).hasMessageContaining("denied").hasMessageContaining("expired");
        }
    }

    @Test
    void signatureFailurePreservesCause() throws Exception {
        Exception failure = new Exception("invalid key");
        try (MockedStatic<Signature> signature = mockStatic(Signature.class)) {
            signature.when(() -> Signature.rsaCheck("cipher", "signature")).thenThrow(failure);
            assertThatThrownBy(() -> new PhoneNumberServiceImpl().getPhoneNumber("cipher", "signature")).hasCause(failure);
        }
    }
}
