package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.OrderShippingService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.wxa.ShippingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Slf4j
@Service
public class OrderShippingServiceImpl implements OrderShippingService {

    @Override
    public BaseResult uploadShippingInfo(String accessToken,
                                         ShippingInfo shippingInfo) throws RuntimeException {
        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken),
                    JSON.toJSONString(shippingInfo)), BaseResult.class);
        } catch (Exception e) {
            log.error("{}", shippingInfo, e);
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
