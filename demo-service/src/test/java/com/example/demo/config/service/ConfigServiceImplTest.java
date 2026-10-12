package com.example.demo.config.service;

import com.example.demo.config.api.bo.Config;
import com.example.demo.config.dao.dataobject.ConfigDO;
import com.example.demo.config.dao.mapper.ConfigMapper;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.support.ServiceTestSupport;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigInteger;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigServiceImplTest {
    private final BigInteger id = BigInteger.TEN;
    private final ConfigMapper mapper = mock(ConfigMapper.class);
    private final ConfigServiceImpl service = spy(new ConfigServiceImpl());
    @BeforeEach
    void initialize() { ServiceTestSupport.mapper(service, mapper, ConfigDO.class); }

    @Test
    void missingQueryReturnsDefaultsWithoutDatabaseAccess() {
        assertThat(service.countConfig(null)).isZero();
        assertThat(service.listConfigs(null)).isEmpty();
        assertThat(service.getConfig(null, " ")).isNull();
        verifyNoInteractions(mapper);
    }

    @Test
    void countUsesLongAndPreservesSearchCriteria() {
        Config query = new Config(); query.setSearch("needle");
        when(mapper.countConfig(any())).thenReturn(3000000000L);
        assertThat(service.countConfig(query)).isEqualTo(3000000000L);
        var capture = ArgumentCaptor.forClass(ConfigDO.class);
        verify(mapper).countConfig(capture.capture());
        assertThat(capture.getValue().getSearch()).isEqualTo("needle");
    }

    @Test
    void listMapsIdentifiersAndPreservesPagination() {
        Config query = new Config(); query.setPageNo(2L); query.setPageSize(20L);
        ConfigDO row = new ConfigDO(); row.setId(id);
        when(mapper.listConfigs(any())).thenReturn(List.of(row));
        assertThat(service.listConfigs(query)).extracting(Config::getId).containsExactly(id);
        var capture = ArgumentCaptor.forClass(ConfigDO.class);
        verify(mapper).listConfigs(capture.capture());
        assertThat(capture.getValue().getPageNo()).isEqualTo(2L);
        assertThat(capture.getValue().getPageSize()).isEqualTo(20L);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void missingRowsProduceEmptyList(boolean nullList) {
        when(mapper.listConfigs(any())).thenReturn(nullList ? null : List.of());
        assertThat(service.listConfigs(new Config())).isEmpty();
    }

    @Test
    void lookupIncludesIdAndKeyOrUsesKeyAlone() {
        ConfigDO row = new ConfigDO(); row.setId(id);
        when(mapper.getConfig(any())).thenReturn(row);
        assertThat(service.getConfig("10", "site-name").getId()).isEqualTo(id);
        assertThat(service.getConfig(null, "site-name").getId()).isEqualTo(id);
        var capture = ArgumentCaptor.forClass(ConfigDO.class);
        verify(mapper, times(2)).getConfig(capture.capture());
        assertThat(capture.getAllValues().getFirst().getId()).isEqualTo(id);
        assertThat(capture.getAllValues().get(1).getId()).isNull();
        assertThat(capture.getAllValues()).extracting(ConfigDO::getKey).containsExactly("site-name", "site-name");
    }

    @Test
    void insertionSetsCreatorAndReturnsGeneratedIdentifier() {
        doAnswer(invocation -> { ConfigDO row = invocation.getArgument(0); assertThat(row.getCreateBy()).isEqualTo("creator"); row.setId(id); return true; }).when(service).save(any(ConfigDO.class));
        Config entity = new Config();
        assertThat(service.insertConfig(entity, "creator")).isSameAs(entity);
        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void databaseInsertionFailurePropagates() {
        RuntimeException failure = new RuntimeException("database unavailable");
        doThrow(failure).when(service).save(any(ConfigDO.class));
        assertThatThrownBy(() -> service.insertConfig(new Config(), "creator")).isSameAs(failure);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void updateScopesIdentifierAndSetsModifierOrRejectsNoAffectedRows(boolean success) {
        doReturn(success).when(service).updateById(any(ConfigDO.class));
        Config changed = new Config();
        if (success) assertThat(service.updateConfig(id, changed, "editor")).isSameAs(changed);
        else assertThatThrownBy(() -> service.updateConfig(id, changed, "editor")).isInstanceOf(ServiceException.class);
        var capture = ArgumentCaptor.forClass(ConfigDO.class);
        verify(service).updateById(capture.capture());
        assertThat(capture.getValue().getId()).isEqualTo(id);
        assertThat(capture.getValue().getUpdateBy()).isEqualTo("editor");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void deleteUsesLogicalFlagAndRequestedIdentifier(boolean success) {
        doReturn(success).when(service).update(any(Wrapper.class));
        if (success) assertThat(service.deleteConfig(id, "editor").getId()).isEqualTo(id);
        else assertThatThrownBy(() -> service.deleteConfig(id, "editor")).isInstanceOf(ServiceException.class);
        ArgumentCaptor<Wrapper<ConfigDO>> capture = ArgumentCaptor.captor();
        verify(service).update(capture.capture());
        var wrapper = (LambdaUpdateWrapper<ConfigDO>) capture.getValue();
        assertThat(wrapper.getSqlSegment()).contains("id");
        assertThat(wrapper.getSqlSet()).contains("deleted", "update_by");
        assertThat(wrapper.getParamNameValuePairs().values()).contains(id, true, "editor");
    }

}
