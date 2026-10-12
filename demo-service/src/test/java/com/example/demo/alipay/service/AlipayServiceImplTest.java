package com.example.demo.alipay.service;

import com.example.demo.alipay.api.FactoryPaymentWapService;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlipayServiceImplTest {
    @Test
    void wapPaymentPassesMerchantArgumentsAndReturnsHtml() {
        var remote = mock(FactoryPaymentWapService.class);
        var response = new AlipayTradeWapPayResponse();
        response.body = "<form>payment</form>";
        when(remote.pay("auth", "subject", "order", "12.30", null, null)).thenReturn(response);
        var service = new AlipayServiceImpl();
        ReflectionTestUtils.setField(service, "factoryPaymentWapService", remote);
        assertThat(service.pay("auth", "subject", "order", "12.30")).isEqualTo("<form>payment</form>");
        verify(remote).pay("auth", "subject", "order", "12.30", null, null);
    }

    @Test
    void paymentFailurePropagates() {
        var remote = mock(FactoryPaymentWapService.class);
        when(remote.pay(any(), any(), any(), any(), any(), any())).thenThrow(new RuntimeException("payment failed"));
        var service = new AlipayServiceImpl();
        ReflectionTestUtils.setField(service, "factoryPaymentWapService", remote);
        assertThatThrownBy(() -> service.pay("auth", "subject", "order", "12.30")).hasMessage("payment failed");
    }
}
