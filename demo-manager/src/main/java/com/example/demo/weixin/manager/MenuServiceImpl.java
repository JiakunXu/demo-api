package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MenuService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.menu.Menu;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    public BaseResult create(String accessToken, Menu menu) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (menu == null) {
            throw new RuntimeException("menu cannot be null.");
        }

        BaseResult result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(HTTPS_CREATE_URL + accessToken, JSON.toJSONString(menu)),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(menu.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
