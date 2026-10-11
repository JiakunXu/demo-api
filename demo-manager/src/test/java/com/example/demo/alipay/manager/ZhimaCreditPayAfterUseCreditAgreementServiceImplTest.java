package com.example.demo.alipay.manager;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericSDKResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ZhimaCreditPayAfterUseCreditAgreementServiceImplTest {
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void sign0BuildsRequestAndReturnsResult() throws Exception {
        String outAgreementNo = "outAgreementNo-value";
        String zmServiceId = "zmServiceId-value";
        String categoryId = "categoryId-value";
        ZhimaCreditPayAfterUseCreditAgreementServiceImpl target = new ZhimaCreditPayAfterUseCreditAgreementServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericSDKResponse response = new AlipayOpenApiGenericSDKResponse();
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_agreement_no", outAgreementNo);
        expectedBiz.put("zm_service_id", zmServiceId);
        expectedBiz.put("category_id", categoryId);

        when(client.sdkExecute(eq("zhima.credit.payafteruse.creditagreement.sign"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.sign(outAgreementNo, zmServiceId, categoryId)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).sdkExecute(eq("zhima.credit.payafteruse.creditagreement.sign"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void sign0PreservesSdkFailure() throws Exception {
        String outAgreementNo = "outAgreementNo-value";
        String zmServiceId = "zmServiceId-value";
        String categoryId = "categoryId-value";
        ZhimaCreditPayAfterUseCreditAgreementServiceImpl target = new ZhimaCreditPayAfterUseCreditAgreementServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.sdkExecute(eq("zhima.credit.payafteruse.creditagreement.sign"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.sign(outAgreementNo, zmServiceId, categoryId)).hasCause(failure);
        }
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void query1BuildsRequestAndReturnsResult() throws Exception {
        String outAgreementNo = "outAgreementNo-value";
        String creditAgreementId = "creditAgreementId-value";
        ZhimaCreditPayAfterUseCreditAgreementServiceImpl target = new ZhimaCreditPayAfterUseCreditAgreementServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "10000";
        java.util.Map<String, Object> expectedBiz = new java.util.HashMap<>();
        expectedBiz.put("out_agreement_no", outAgreementNo);
        expectedBiz.put("credit_agreement_id", creditAgreementId);

        when(client.execute(eq("zhima.credit.payafteruse.creditagreement.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThat(target.query(outAgreementNo, creditAgreementId)).isSameAs(response);
            ArgumentCaptor<java.util.Map> biz = ArgumentCaptor.forClass(java.util.Map.class);
            verify(client).execute(eq("zhima.credit.payafteruse.creditagreement.query"), isNull(), biz.capture());
            assertThat(biz.getValue()).isEqualTo(expectedBiz);

        }
    }

    @Test
    void query1PreservesSdkFailure() throws Exception {
        String outAgreementNo = "outAgreementNo-value";
        String creditAgreementId = "creditAgreementId-value";
        ZhimaCreditPayAfterUseCreditAgreementServiceImpl target = new ZhimaCreditPayAfterUseCreditAgreementServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        Exception failure = new Exception("provider unavailable");
        when(client.execute(eq("zhima.credit.payafteruse.creditagreement.query"), isNull(), anyMap())).thenThrow(failure);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(outAgreementNo, creditAgreementId)).hasCause(failure);
        }
    }

    @Test
    void query1RejectsBusinessError() throws Exception {
        String outAgreementNo = "outAgreementNo-value";
        String creditAgreementId = "creditAgreementId-value";
        ZhimaCreditPayAfterUseCreditAgreementServiceImpl target = new ZhimaCreditPayAfterUseCreditAgreementServiceImpl();
        com.alipay.easysdk.util.generic.Client client = mock(com.alipay.easysdk.util.generic.Client.class, RETURNS_SELF);
        AlipayOpenApiGenericResponse response = new AlipayOpenApiGenericResponse();
        response.code = "40004";
        response.subCode = "DENIED";
        response.msg = "Rejected";
        response.subMsg = "Denied";
        when(client.execute(eq("zhima.credit.payafteruse.creditagreement.query"), isNull(), anyMap())).thenReturn(response);
        try (var factory = mockStatic(Factory.Util.class)) {
            factory.when(Factory.Util::Generic).thenReturn(client);
            assertThatThrownBy(() -> target.query(outAgreementNo, creditAgreementId)).hasMessage("调用失败，原因：Rejected，Denied");
        }
    }
}
