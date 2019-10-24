package com.example.demo.api.shop;

import com.example.demo.api.shop.bo.Shop;

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
