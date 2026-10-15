package com.example.demo.alipay.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.Client;
import com.example.demo.alipay.api.bo.fund.*;
import com.example.demo.alipay.dao.dataobject.*;
import com.example.demo.alipay.dao.mapper.*;
import com.example.demo.support.ServiceTestSupport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlipayFundAuthNotifyServiceImplTest {
    private final AlipayFundAuthNotifyServiceImpl service = spy(new AlipayFundAuthNotifyServiceImpl());
    @BeforeEach
    void initialize() { ServiceTestSupport.mapper(service, mock(AlipayFundAuthNotifyMapper.class), AlipayFundAuthNotifyDO.class);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void fundAuthorizationRequiresSignatureAndMapsNotifyFields(boolean valid) throws Exception {
        Client client = mock(Client.class);
        Map<String, String> input = Map.of("auth_no", "auth", "out_order_no", "order", "operation_id", "operation", "amount", "12.30", "notify_time", "2024-02-29 12:34:56");
        when(client.verifyNotify(input)).thenReturn(valid);
        try (var factory = mockStatic(Factory.Payment.class)) {
            factory.when(Factory.Payment::Common).thenReturn(client);
            if (valid) { var result = service.getFundAuthNotify(input);
            assertThat(result.getAuthNo()).isEqualTo("auth");
            assertThat(result.getOutOrderNo()).isEqualTo("order");
            assertThat(result.getOperationId()).isEqualTo("operation");
            assertThat(result.getAmount()).isEqualTo("12.30");
            assertThat(result.getNotifyTime()).isNotNull();
            }
            else assertThatThrownBy(() -> service.getFundAuthNotify(input)).hasMessageContaining("验签失败");
        }
    }

    @Test
    void insertsAuthorizationMetadata() {
        doReturn(true).when(service).save(any(AlipayFundAuthNotifyDO.class));
        var notify = new AlipayFundAuthNotify(); notify.setAuthNo("auth");
        assertThat(service.insertFundAuthNotify(notify)).isSameAs(notify);
        verify(service).save(argThat(row -> "auth".equals(row.getAuthNo())));
    }
}
