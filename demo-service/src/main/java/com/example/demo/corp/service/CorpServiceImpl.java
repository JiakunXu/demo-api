package com.example.demo.corp.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.corp.api.IShopService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.corp.dao.dataobject.ShopDO;
import com.example.demo.corp.dao.mapper.ShopMapper;
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
@Service("com.example.demo.shop.service.shopService")
public class CorpServiceImpl implements CorpService, IShopService {

    private static final Logger logger = LoggerFactory.getLogger(CorpServiceImpl.class);

    @Autowired
    private ShopMapper          shopMapper;

    @Override
    public int countShop() {
        ShopDO shopDO = new ShopDO();

        return count(shopDO);
    }

    @Override
    public List<Corp> listShops() {
        ShopDO shopDO = new ShopDO();

        return BeanUtil.copy(list(shopDO), Corp.class);
    }

    @Override
    public Corp getShop() {
        ShopDO shopDO = new ShopDO();
        shopDO.setId(BigInteger.ONE);

        return BeanUtil.copy(get(shopDO), Corp.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Corp updateShop() {
        ShopDO shopDO = new ShopDO();
        shopDO.setId(BigInteger.ONE);
        shopDO.setName("N");

        try {
            shopMapper.update(shopDO);
        } catch (Exception e) {
            logger.error(shopDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "message");
        }

        return BeanUtil.copy(shopDO, Corp.class);
    }

    private int count(ShopDO shopDO) {
        try {
            return shopMapper.count(shopDO);
        } catch (Exception e) {
            logger.error(shopDO.toString(), e);
        }

        return 0;
    }

    private List<ShopDO> list(ShopDO shopDO) {
        try {
            return shopMapper.list(shopDO);
        } catch (Exception e) {
            logger.error(shopDO.toString(), e);
        }

        return null;
    }

    private ShopDO get(ShopDO shopDO) {
        try {
            return shopMapper.get(shopDO);
        } catch (Exception e) {
            logger.error(shopDO.toString(), e);
        }

        return null;
    }

}
