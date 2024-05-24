package com.example.demo.log.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.log.api.LoginLogService;
import com.example.demo.log.api.bo.LoginLog;
import com.example.demo.log.dao.dataobject.LoginLogDO;
import com.example.demo.log.dao.mapper.LoginLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

    @Autowired
    private LoginLogMapper      loginLogMapper;

    @Override
    public int countLoginLog(LoginLog loginLog) {
        if (loginLog == null) {
            return 0;
        }

        return count(BeanUtil.copy(loginLog, LoginLogDO.class));
    }

    @Override
    public List<LoginLog> listLoginLogs(LoginLog loginLog) {
        if (loginLog == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(loginLog, LoginLogDO.class)), LoginLog.class);
    }

    @Override
    public LoginLog getLoginLog(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        LoginLogDO loginLogDO = new LoginLogDO();
        loginLogDO.setId(new BigInteger(id));

        return BeanUtil.copy(get(loginLogDO), LoginLog.class);
    }

    @Override
    public LoginLog insertLoginLog(@NotNull LoginLog loginLog, @NotBlank String creator) {
        LoginLogDO loginLogDO = BeanUtil.copy(loginLog, LoginLogDO.class);
        loginLogDO.setCreator(creator);

        try {
            loginLogMapper.insert(loginLogDO);
        } catch (Exception e) {
            logger.error(loginLogDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        loginLog.setId(loginLogDO.getId());

        return loginLog;
    }

    private int count(LoginLogDO loginLogDO) {
        try {
            return loginLogMapper.count(loginLogDO);
        } catch (Exception e) {
            logger.error(loginLogDO.toString(), e);
        }

        return 0;
    }

    private List<LoginLogDO> list(LoginLogDO loginLogDO) {
        try {
            return loginLogMapper.list(loginLogDO);
        } catch (Exception e) {
            logger.error(loginLogDO.toString(), e);
        }

        return null;
    }

    private LoginLogDO get(LoginLogDO loginLogDO) {
        try {
            return loginLogMapper.get(loginLogDO);
        } catch (Exception e) {
            logger.error(loginLogDO.toString(), e);
        }

        return null;
    }

}
