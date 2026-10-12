package com.example.demo.alipay.service;

import com.alipay.easysdk.payment.common.models.*;
import com.example.demo.alipay.api.*;
import com.example.demo.alipay.api.bo.*;
import com.example.demo.alipay.api.bo.fund.*;
import com.example.demo.alipay.dao.dataobject.*;
import com.example.demo.alipay.dao.mapper.*;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlipayFundTransOrderServiceImplTest {
    @Test
    void changedCopiesFieldsAndReturnsGeneratedIdentifier() {
        var service = spy(new AlipayFundTransOrderServiceImpl());
        doAnswer(invocation -> { AlipayFundTransOrderDO row = invocation.getArgument(0); row.setId(BigInteger.TEN); return true; }).when(service).save(any(AlipayFundTransOrderDO.class));
        var order = new AlipayFundTransOrder();
        assertThat(service.changed(order)).isSameAs(order); assertThat(order.getId()).isEqualTo(BigInteger.TEN);
    }

    @Test
    void failedPersistenceDoesNotReturnSuccessfulChange() {
        var service = spy(new AlipayFundTransOrderServiceImpl());
        doThrow(new RuntimeException("db failed")).when(service).save(any(AlipayFundTransOrderDO.class));
        assertThatThrownBy(() -> service.changed(new AlipayFundTransOrder())).hasMessage("db failed");
    }
}
