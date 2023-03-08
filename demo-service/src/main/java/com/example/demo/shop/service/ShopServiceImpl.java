package com.example.demo.shop.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.shop.api.IShopService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.shop.dao.dataobject.ShopDO;
import com.example.demo.shop.dao.mapper.ShopMapper;
import com.example.demo.shop.api.ShopService;
import com.example.demo.shop.api.bo.Shop;
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
@Service("shopService")
public class ShopServiceImpl implements ShopService, IShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopMapper          shopMapper;

    @Override
    public int countShop() {
        ShopDO shopDO = new ShopDO();

        return count(shopDO);
    }

    @Override
    public List<Shop> listShops() {
        ShopDO shopDO = new ShopDO();

        return BeanUtil.copy(list(shopDO), Shop::new);
    }

    @Override
    public Shop getShop() {
        ShopDO shopDO = new ShopDO();
        shopDO.setId(BigInteger.ONE);

        return BeanUtil.copy(get(shopDO), Shop::new);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Shop updateShop() {
        ShopDO shopDO = new ShopDO();
        shopDO.setId(BigInteger.ONE);
        shopDO.setName("N");

        try {
            shopMapper.update(shopDO);
        } catch (Exception e) {
            logger.error(shopDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "message");
        }

        return BeanUtil.copy(shopDO, Shop::new);
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
