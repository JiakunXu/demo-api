package com.example.demo.shop.controller;

import com.example.demo.api.shop.ShopService;
import com.example.demo.api.shop.bo.Shop;
import com.example.demo.api.user.bo.User;
import com.example.demo.framework.controller.BaseController;
import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JiakunXu
 */
@RestController
@RequestMapping(value = "/api/shop")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<Shop> listShops(HttpServletRequest request, HttpServletResponse response) {
        int count = shopService.countShop();

        if (count == 0) {
            return new ListResponse<>(0, null);
        }

        return new ListResponse<>(count, shopService.listShops());
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ObjectResponse<Shop> getShop(HttpServletRequest request, HttpServletResponse response) {
        String name = this.getParameter(request, "name");
        User user = this.getParameter(request, User.class);

        return new ObjectResponse<>(shopService.getShop());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ObjectResponse<Shop> updateShop(HttpServletRequest request,
                                           HttpServletResponse response) {
        String name = this.getParameter(request, "name");
        User user = this.getParameter(request, User.class);

        return new ObjectResponse<>(shopService.updateShop());
    }

}
