package com.example.demo.corp.api;

import com.example.demo.corp.api.bo.Corp;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface CorpService {

    /**
     * 
     * @return
     */
    int countShop();

    /**
     * 
     * @return
     */
    List<Corp> listShops();

    /**
     * 
     * @return
     */
    Corp getShop();

    /**
     * 
     * @return
     */
    Corp updateShop();

}
