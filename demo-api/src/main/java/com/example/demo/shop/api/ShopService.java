package com.example.demo.shop.api;

import com.example.demo.shop.api.bo.Shop;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface ShopService {

    /**
     * 
     * @return
     */
    int countShop();

    /**
     * 
     * @return
     */
    List<Shop> listShops();

    /**
     * 
     * @return
     */
    Shop getShop();

    /**
     * 
     * @return
     */
    Shop updateShop();

}
