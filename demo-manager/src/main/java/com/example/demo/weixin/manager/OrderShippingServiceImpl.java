package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.OrderShippingService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.ShippingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class OrderShippingServiceImpl implements OrderShippingService {

    private static final Logger logger = LoggerFactory.getLogger(OrderShippingServiceImpl.class);

    @Override
    public BaseResult uploadShippingInfo(String accessToken,
                                         ShippingInfo shippingInfo) throws RuntimeException {
        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                    JSON.toJSONString(shippingInfo)), BaseResult.class);
        } catch (Exception e) {
            logger.error(shippingInfo.toString(), e);
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
