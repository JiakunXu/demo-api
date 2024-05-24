package com.example.demo.corp.service;

import com.example.demo.corp.dao.dataobject.CorpDO;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.corp.api.ICorpService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.corp.dao.mapper.CorpMapper;
import com.example.demo.corp.api.CorpService;
import com.example.demo.corp.api.bo.Corp;
import org.apache.commons.lang3.StringUtils;
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
    public int countCorp(Corp corp) {
        if (corp == null) {
            return 0;
        }

        return count(BeanUtil.copy(corp, CorpDO.class));
    }

    @Override
    public List<Corp> listCorps(Corp corp) {
        if (corp == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(corp, CorpDO.class)), Corp.class);
    }

    @Override
    public Corp getCorp(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getCorp(new BigInteger(id));
    }

    @Override
    public Corp getCorp(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(get(new CorpDO(id)), Corp.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Corp updateCorp(@NotNull BigInteger id, @NotNull Corp corp, @NotBlank String modifier) {
        corp.setId(id);

        CorpDO corpDO = BeanUtil.copy(corp, CorpDO.class);
        corpDO.setModifier(modifier);

        try {
            if (corpMapper.update(corpDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(corpDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return corp;
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
