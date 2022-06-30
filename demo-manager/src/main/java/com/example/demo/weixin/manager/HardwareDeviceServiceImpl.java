package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.HardwareDeviceService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.device.Device;
import com.example.demo.weixin.api.bo.device.SnTicket;
import com.example.demo.weixin.api.bo.device.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class HardwareDeviceServiceImpl implements HardwareDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(HardwareDeviceServiceImpl.class);

    @Override
    public SnTicket getSnTicket(String accessToken, Device device) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (device == null) {
            throw new RuntimeException("device cannot be null.");
        }

        SnTicket snTicket;

        try {
            snTicket = JSON.parseObject(HttpUtil
                .post(HardwareDeviceService.HTTPS_GET_URL + accessToken, JSON.toJSONString(device)),
                SnTicket.class);
        } catch (Exception e) {
            logger.error(device.toString(), e);

            throw new RuntimeException(e);
        }

        if (snTicket == null) {
            throw new RuntimeException("snTicket is null.");
        }

        if (snTicket.getErrCode() != 0) {
            throw new RuntimeException(snTicket.getErrMsg());
        }

        return snTicket;
    }

    @Override
    public BaseResult send(String accessToken, Message message) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (message == null) {
            throw new RuntimeException("message cannot be null.");
        }

        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(HardwareDeviceService.HTTPS_SEND_URL + accessToken,
                    JSON.toJSONString(message)), BaseResult.class);
        } catch (Exception e) {
            logger.error(message.toString(), e);

            throw new RuntimeException(e);
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
