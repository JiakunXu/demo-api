package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MenuService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Override
    public BaseResult create(String accessToken, Menu menu) throws RuntimeException {
        BaseResult result;

        try {
            result = JSON.parseObject(HttpUtil
                .post(MessageFormat.format(HTTPS_POST_URL, accessToken), JSON.toJSONString(menu)),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(menu.toString(), e);
            throw new RuntimeException(e.getMessage(), e);
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
