package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.HardwareDeviceService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.device.Device;
import com.example.demo.weixin.api.bo.device.SnTicket;
import com.example.demo.weixin.api.bo.device.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class HardwareDeviceServiceImpl implements HardwareDeviceService {

    @Override
    public SnTicket getSnTicket(String accessToken, Device device) throws RuntimeException {
        SnTicket snTicket;

        try {
            snTicket = JSON.parseObject(HttpUtil
                .post(MessageFormat.format(HTTPS_GET_URL, accessToken), JSON.toJSONString(device)),
                SnTicket.class);
        } catch (Exception e) {
            log.error("{}", device, e);
            throw new RuntimeException(e.getMessage(), e);
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
        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_SEND_URL, accessToken),
                    JSON.toJSONString(message)), BaseResult.class);
        } catch (Exception e) {
            log.error("{}", message, e);
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
