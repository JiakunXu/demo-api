package com.example.demo.corp.service;

import com.example.demo.corp.dao.dataobject.CorpDO;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.corp.api.ICorpService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.corp.dao.mapper.CorpMapper;
import com.example.demo.corp.api.CorpService;
import com.example.demo.corp.api.bo.Corp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.corp.service.corpService")
public class CorpServiceImpl implements CorpService, ICorpService {

    private static final Logger logger = LoggerFactory.getLogger(CorpServiceImpl.class);

    @Autowired
    private CorpMapper          corpMapper;

    @Override
    public int countCorp() {
        CorpDO corpDO = new CorpDO();

        return count(corpDO);
    }

    @Override
    public List<Corp> listCorps() {
        CorpDO corpDO = new CorpDO();

        return BeanUtil.copy(list(corpDO), Corp.class);
    }

    @Override
    public Corp getCorp() {
        CorpDO corpDO = new CorpDO();
        corpDO.setId(BigInteger.ONE);

        return BeanUtil.copy(get(corpDO), Corp.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Corp updateCorp() {
        CorpDO corpDO = new CorpDO();
        corpDO.setId(BigInteger.ONE);
        corpDO.setName("N");

        try {
            corpMapper.update(corpDO);
        } catch (Exception e) {
            logger.error(corpDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "message");
        }

        return BeanUtil.copy(corpDO, Corp.class);
    }

    private int count(CorpDO corpDO) {
        try {
            return corpMapper.count(corpDO);
        } catch (Exception e) {
            logger.error(corpDO.toString(), e);
        }

        return 0;
    }

    private List<CorpDO> list(CorpDO corpDO) {
        try {
            return corpMapper.list(corpDO);
        } catch (Exception e) {
            logger.error(corpDO.toString(), e);
        }

        return null;
    }

    private CorpDO get(CorpDO corpDO) {
        try {
            return corpMapper.get(corpDO);
        } catch (Exception e) {
            logger.error(corpDO.toString(), e);
        }

        return null;
    }

}
