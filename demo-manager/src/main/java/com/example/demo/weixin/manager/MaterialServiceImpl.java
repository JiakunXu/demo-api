package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MaterialService;
import com.example.demo.weixin.api.bo.material.Count;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Override
    public Count count(String accessToken) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        Count count;

        try {
            count = JSON.parseObject(HttpUtil.get(HTTPS_COUNT_URL + accessToken), Count.class);
        } catch (Exception e) {
            logger.error(accessToken, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (count == null) {
            throw new RuntimeException("count is null.");
        }

        if (count.getErrCode() != 0) {
            throw new RuntimeException(count.getErrMsg());
        }

        return count;
    }

}
