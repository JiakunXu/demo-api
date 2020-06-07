package com.example.demo.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.shop.IShopService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.shop.dao.ShopDao;
import com.example.demo.api.shop.ShopService;
import com.example.demo.api.shop.bo.Shop;
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
    private ShopDao             shopDao;

    @Override
    public int countShop() {
        Shop shop = new Shop();

        return countShop(shop);
    }

    @Override
    public List<Shop> listShops() {
        Shop shop = new Shop();

        return listShops(shop);
    }

    @Override
    public Shop getShop() {
        Shop shop = new Shop();
        shop.setId(BigInteger.ONE);

        return getShop(shop);
    }

    @Override
    @Transactional
    public Shop updateShop() {
        Shop shop = new Shop();
        shop.setId(BigInteger.ONE);
        shop.setName("N");

        try {
            shopDao.updateShop(shop);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(shop), e);
            throw new ServiceException("errorCode", "message");
        }

        return shop;
    }

    private int countShop(Shop shop) {
        try {
            return shopDao.countShop(shop);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(shop), e);
        }

        return 0;
    }

    private List<Shop> listShops(Shop shop) {
        try {
            return shopDao.listShops(shop);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(shop), e);
        }

        return null;
    }

    private Shop getShop(Shop shop) {
        try {
            return shopDao.getShop(shop);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(shop), e);
        }

        return null;
    }

}
