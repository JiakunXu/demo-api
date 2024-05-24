package com.example.demo.alipay.service;

import com.example.demo.alipay.api.AlipayFundTransOrderService;
import com.example.demo.alipay.api.bo.fund.AlipayFundTransOrder;
import com.example.demo.alipay.dao.dataobject.AlipayFundTransOrderDO;
import com.example.demo.alipay.dao.mapper.AlipayFundTransOrderMapper;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayFundTransOrderServiceImpl implements AlipayFundTransOrderService {

    private static final Logger        logger = LoggerFactory
        .getLogger(AlipayFundTransOrderServiceImpl.class);

    @Autowired
    private AlipayFundTransOrderMapper alipayFundTransOrderMapper;

    @Override
    public AlipayFundTransOrder changed(@NotNull AlipayFundTransOrder alipayFundTransOrder) {
        AlipayFundTransOrderDO alipayFundTransOrderDO = BeanUtil.copy(alipayFundTransOrder,
            AlipayFundTransOrderDO::new);

        try {
            alipayFundTransOrderMapper.insert(alipayFundTransOrderDO);
        } catch (Exception e) {
            logger.error(alipayFundTransOrderDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return alipayFundTransOrder;
    }

}
