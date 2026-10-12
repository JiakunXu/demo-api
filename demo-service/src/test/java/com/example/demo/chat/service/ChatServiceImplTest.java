package com.example.demo.chat.service;

import com.example.demo.chat.api.*;
import com.example.demo.chat.api.bo.*;
import com.example.demo.chat.dao.mapper.ChatMapper;
import com.example.demo.chat.dao.dataobject.ChatDO;
import com.example.demo.user.api.UserService;
import com.example.demo.user.api.bo.User;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.support.ServiceTestSupport;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatServiceImplTest {
    private final ChatMapper mapper = mock(ChatMapper.class);
    private final UserService users = mock(UserService.class);
    private final ChatDetailService details = mock(ChatDetailService.class);
    private final ChatStatusService statuses = mock(ChatStatusService.class);
    private final ChatServiceImpl service = spy(new ChatServiceImpl());
    private final BigInteger user = BigInteger.TEN;
    private final BigInteger friend = BigInteger.ONE;

    @BeforeEach
    void initialize() {
        ServiceTestSupport.mapper(service, mapper, ChatDO.class);
        ReflectionTestUtils.setField(service, "userService", users);
        ReflectionTestUtils.setField(service, "chatDetailService", details);
        ReflectionTestUtils.setField(service, "chatStatusService", statuses);
    }

    @Test
    void updateExistingChatDoesNotInsertOrOverwriteItsId() {
        Chat chat = new Chat(); chat.setId(BigInteger.valueOf(99));
        when(mapper.updateChat(any())).thenReturn(1);
        assertThat(service.saveOrUpdate(user, friend, chat)).isSameAs(chat);
        verify(service, never()).save(any(ChatDO.class));
        assertThat(chat.getId()).isEqualTo(BigInteger.valueOf(99));
        var capture = ArgumentCaptor.forClass(ChatDO.class);
        verify(mapper).updateChat(capture.capture());
        assertThat(capture.getValue().getUserId()).isEqualTo(user);
        assertThat(capture.getValue().getFriendId()).isEqualTo(friend);
        assertThat(capture.getValue().getCreator()).isEqualTo("10");
        assertThat(capture.getValue().getModifier()).isEqualTo("10");
    }

    @Test
    void missingChatInsertsAndCopiesGeneratedIdBack() {
        doAnswer(invocation -> { ((ChatDO) invocation.getArgument(0)).setId(BigInteger.valueOf(99)); return true; }).when(service).save(any(ChatDO.class));
        Chat chat = new Chat();
        assertThat(service.saveOrUpdate(user, friend, chat).getId()).isEqualTo(BigInteger.valueOf(99));
        var sequence = inOrder(mapper, service);
        sequence.verify(mapper).updateChat(any());
        sequence.verify(service).save(any(ChatDO.class));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void updateOrInsertExceptionBecomesBusinessFailure(boolean updateFailure) {
        RuntimeException failure = new RuntimeException("database failed");
        if (updateFailure) when(mapper.updateChat(any())).thenThrow(failure);
        else doThrow(failure).when(service).save(any(ChatDO.class));
        assertThatThrownBy(() -> service.saveOrUpdate(user, friend, new Chat())).isInstanceOf(ServiceException.class).hasMessage("信息更新失败，请稍后再试");
        if (updateFailure) verify(service, never()).save(any(ChatDO.class));
    }

    @Test
    void missingIdentifiersAreRejectedBeforeDatabaseAccess() {
        assertThatThrownBy(() -> service.saveOrUpdate(null, friend, new Chat())).hasMessage("参数信息不能为空");
        assertThatThrownBy(() -> service.saveOrUpdate(user, null, new Chat())).hasMessage("参数信息不能为空");
        assertThatThrownBy(() -> service.saveOrUpdate(user, friend, null)).hasMessage("参数信息不能为空");
        verifyNoInteractions(mapper);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void readingSetsUnreadToZeroOnlyForRequestedUser(int rows) {
        when(mapper.updateUnread(any())).thenReturn(rows);
        if (rows == 1) assertThat(service.read(user, friend).getUnread()).isZero();
        else assertThatThrownBy(() -> service.read(user, friend)).hasMessage("信息不存在");
        var capture = ArgumentCaptor.forClass(ChatDO.class);
        verify(mapper).updateUnread(capture.capture());
        assertThat(capture.getValue().getId()).isEqualTo(friend);
        assertThat(capture.getValue().getUserId()).isEqualTo(user);
        assertThat(capture.getValue().getUnread()).isZero();
    }

    @Test
    void listConvertsPaginationAndEnrichesFriendAndLatestMessage() {
        ChatDO row = new ChatDO(); row.setFriendId(friend); row.setChatDetailId(BigInteger.valueOf(99));
        when(mapper.listChats(any())).thenReturn(List.of(row));
        User friendUser = new User(); ChatDetail detail = new ChatDetail();
        when(users.getUser(friend)).thenReturn(friendUser);
        when(details.getChatDetail(user, row.getChatDetailId())).thenReturn(detail);
        List<Chat> result = service.listChats(user, "2", "20");
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getFriend()).isSameAs(friendUser);
        assertThat(result.getFirst().getChatDetail()).isSameAs(detail);
        var capture = ArgumentCaptor.forClass(ChatDO.class);
        verify(mapper).listChats(capture.capture());
        assertThat(capture.getValue().getPageNo()).isEqualTo(2L);
        assertThat(capture.getValue().getPageSize()).isEqualTo(20L);
        assertThat(capture.getValue().getUserId()).isEqualTo(user);
    }

    @Test
    void emptyAndInvalidQueriesAvoidEnrichment() {
        assertThat(service.countChat(null)).isZero();
        assertThat(service.listChats(null, "1", "20")).isEmpty();
        assertThat(service.listChats(user, "", "20")).isEmpty();
        assertThat(service.getChat(user, (BigInteger) null)).isNull();
        assertThat(service.getChat(null, "1")).isNull();
        verifyNoInteractions(mapper, users, details, statuses);
        assertThat(service.listChats(user, "1", "20")).isEmpty();
        verifyNoInteractions(users, details, statuses);
    }

    @Test
    void countUsesLongAndGetByPairIncludesBothIdentifiers() {
        when(mapper.countChat(any())).thenReturn(3000000000L);
        assertThat(service.countChat(user)).isEqualTo(3000000000L);
        ChatDO row = new ChatDO(); row.setId(BigInteger.valueOf(99));
        when(mapper.getChat(any())).thenReturn(row);
        assertThat(service.getChat(user, friend).getId()).isEqualTo(row.getId());
        var capture = ArgumentCaptor.forClass(ChatDO.class);
        verify(mapper).getChat(capture.capture());
        assertThat(capture.getValue().getUserId()).isEqualTo(user);
        assertThat(capture.getValue().getFriendId()).isEqualTo(friend);
    }

    @Test
    void stringFriendLookupAssemblesUserAndOnlineStatus() {
        User friendUser = new User();
        when(users.getUser(friend)).thenReturn(friendUser);
        when(statuses.getStatus(user, "1")).thenReturn("online");
        Chat chat = service.getChat(user, "1");
        assertThat(chat.getUserId()).isEqualTo(user);
        assertThat(chat.getFriend()).isSameAs(friendUser);
        assertThat(chat.getStatus()).isEqualTo("online");
        verifyNoInteractions(mapper);
    }
}
