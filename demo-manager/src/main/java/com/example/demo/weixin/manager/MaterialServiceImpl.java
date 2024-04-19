package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MaterialService;
import com.example.demo.weixin.api.bo.material.Count;
import com.example.demo.weixin.api.bo.material.Material;
import com.example.demo.weixin.api.bo.material.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    private static final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Override
    public Count count(String accessToken) throws RuntimeException {
        Count count;

        try {
            count = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_COUNT_URL, accessToken)), Count.class);
        } catch (Exception e) {
            logger.error(accessToken, e);
            throw new RuntimeException(e);
        }

        if (count == null) {
            throw new RuntimeException("count is null.");
        }

        if (count.getErrCode() != 0) {
            throw new RuntimeException(count.getErrMsg());
        }

        return count;
    }

    @Override
    public Material list(String accessToken, Parameter parameter) throws RuntimeException {
        Material material;

        try {
            material = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_LIST_URL, accessToken),
                    JSON.toJSONString(parameter)), Material.class);
        } catch (Exception e) {
            logger.error(accessToken, e);
            throw new RuntimeException(e);
        }

        if (material == null) {
            throw new RuntimeException("material is null.");
        }

        if (material.getErrCode() != 0) {
            throw new RuntimeException(material.getErrMsg());
        }

        return material;
    }

}
