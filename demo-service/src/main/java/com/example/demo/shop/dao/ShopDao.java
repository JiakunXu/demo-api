package com.example.demo.shop.dao;

import com.example.demo.api.shop.bo.Shop;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface ShopDao {

    /**
     * 
     * @param shop
     * @return
     */
    int countShop(Shop shop);

    /**
     *
     * @param shop
     * @return
     */
    List<Shop> listShops(Shop shop);

    /**
     * 
     * @param shop
     * @return
     */
    Shop getShop(Shop shop);

    /**
     * 
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

}
