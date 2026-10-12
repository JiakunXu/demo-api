package com.example.demo.tunnel.service;

import com.example.demo.tunnel.dao.dataobject.TunnelDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.support.ServiceTestSupport;
import com.example.demo.tunnel.dao.mapper.TunnelMapper;
import com.example.demo.framework.exception.ServiceException;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigInteger;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TunnelServiceImplTest {
    private final TunnelServiceImpl service = spy(new TunnelServiceImpl());
    @BeforeEach
    void initialize() { ServiceTestSupport.mapper(service, mock(TunnelMapper.class), TunnelDO.class); }
    @Test
    void insertionGeneratesTunnelIdAndCopiesPersistenceId() {
        doAnswer(invocation -> { TunnelDO row = invocation.getArgument(0); row.setId(BigInteger.TEN); return true; }).when(service).save(any(TunnelDO.class));
        var result = service.insertTunnel(BigInteger.ONE, "host", "creator");
        assertThat(result.getTunnelId()).matches("[0-9a-f-]{36}");
        assertThat(result.getId()).isEqualTo(BigInteger.TEN);
        assertThat(result.getUserId()).isEqualTo(BigInteger.ONE);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void deletionUsesTunnelIdentifierAndLogicalFlag(boolean success) {
        doReturn(success).when(service).update(any(Wrapper.class));
        if (success) assertThat(service.deleteTunnel("tunnel", "editor").getTunnelId()).isEqualTo("tunnel");
        else assertThatThrownBy(() -> service.deleteTunnel("tunnel", "editor")).isInstanceOf(ServiceException.class);
        ArgumentCaptor<Wrapper<TunnelDO>> capture = ArgumentCaptor.captor(); verify(service).update(capture.capture());
        var wrapper = (LambdaUpdateWrapper<TunnelDO>) capture.getValue();
        assertThat(wrapper.getSqlSegment()).contains("tunnel_id");
        assertThat(wrapper.getSqlSet()).contains("deleted", "modifier");
        assertThat(wrapper.getParamNameValuePairs().values()).contains("tunnel", true, "editor");
    }
}
