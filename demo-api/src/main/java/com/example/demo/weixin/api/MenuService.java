package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.menu.Menu;

/**
 * @author JiakunXu
 */
public interface MenuService {

    String HTTPS_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    /**
     * 自定义菜单 / 创建接口.
     * 
     * @param accessToken
     * @param menu
     * @return
     * @throws RuntimeException
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html">微信官方文档</a>
     */
    BaseResult create(String accessToken, Menu menu) throws RuntimeException;

}
