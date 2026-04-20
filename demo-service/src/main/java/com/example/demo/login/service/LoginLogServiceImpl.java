package com.example.demo.login.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.login.api.LoginLogService;
import com.example.demo.login.api.bo.LoginLog;
import com.example.demo.login.dao.dataobject.LoginLogDO;
import com.example.demo.login.dao.mapper.LoginLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO>
                                 implements LoginLogService {

    @Override
    public int countLog(LoginLog log) {
        if (log == null) {
            return 0;
        }

        return this.count(BeanUtil.copy(log, LoginLogDO.class));
    }

    @Override
    public List<LoginLog> listLogs(LoginLog log) {
        if (log == null) {
            return List.of();
        }

        List<LoginLog> list = BeanUtil.copy(this.list(BeanUtil.copy(log, LoginLogDO.class)),
            LoginLog.class);

        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }

        return list;
    }

    @Override
    public LoginLog getLog(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getLog(new BigInteger(id));
    }

    @Override
    public LoginLog getLog(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(this.get(new LoginLogDO(id)), LoginLog.class);
    }

    @Override
    public LoginLog insertLog(@NotNull LoginLog log, @NotBlank String creator) {
        LoginLogDO logDO = BeanUtil.copy(log, LoginLogDO.class);
        logDO.setCreator(creator);

        try {
            this.insert(logDO);
        } catch (Exception e) {
            LoginLogServiceImpl.log.error("{}", logDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        log.setId(logDO.getId());

        return log;
    }

}
