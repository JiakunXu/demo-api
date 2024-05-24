package com.example.demo.log.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.log.api.OperLogService;
import com.example.demo.log.api.bo.OperLog;
import com.example.demo.log.dao.dataobject.OperLogDO;
import com.example.demo.log.dao.mapper.OperLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class OperLogServiceImpl implements OperLogService {

    private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);

    @Autowired
    private OperLogMapper       operLogMapper;

    @Override
    public int countOperLog(OperLog operLog) {
        if (operLog == null) {
            return 0;
        }

        return count(BeanUtil.copy(operLog, OperLogDO.class));
    }

    @Override
    public List<OperLog> listOperLogs(OperLog operLog) {
        if (operLog == null) {
            return null;
        }

        if (StringUtils.isAnyBlank(operLog.getSort(), operLog.getDir())) {
            operLog.setSort("id");
            operLog.setDir("DESC");
        }

        return BeanUtil.copy(list(BeanUtil.copy(operLog, OperLogDO.class)), OperLog.class);
    }

    @Override
    public OperLog getOperLog(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return BeanUtil.copy(get(new OperLogDO(new BigInteger(id))), OperLog.class);
    }

    @Override
    public OperLog insertOperLog(@NotNull OperLog operLog, @NotBlank String creator) {
        OperLogDO operLogDO = BeanUtil.copy(operLog, OperLogDO.class);
        operLogDO.setCreator(creator);

        try {
            operLogMapper.insert(operLogDO);
        } catch (Exception e) {
            logger.error(operLogDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        operLog.setId(operLogDO.getId());

        return operLog;
    }

    private int count(OperLogDO operLogDO) {
        try {
            return operLogMapper.count(operLogDO);
        } catch (Exception e) {
            logger.error(operLogDO.toString(), e);
        }

        return 0;
    }

    private List<OperLogDO> list(OperLogDO operLogDO) {
        try {
            return operLogMapper.list(operLogDO);
        } catch (Exception e) {
            logger.error(operLogDO.toString(), e);
        }

        return null;
    }

    private OperLogDO get(OperLogDO operLogDO) {
        try {
            return operLogMapper.get(operLogDO);
        } catch (Exception e) {
            logger.error(operLogDO.toString(), e);
        }

        return null;
    }

}
