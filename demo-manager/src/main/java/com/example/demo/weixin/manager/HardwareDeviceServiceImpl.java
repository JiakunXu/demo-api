package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.HardwareDeviceService;
import com.example.demo.weixin.api.bo.device.Hardware;
import com.example.demo.weixin.api.bo.device.SnTicket;
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
    public SnTicket getSnTicket(String accessToken, Hardware hardware) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (hardware == null) {
            throw new RuntimeException("hardware cannot be null.");
        }

        SnTicket snTicket = null;

        try {
            snTicket = JSON
                .parseObject(HttpUtil.post(HardwareDeviceService.HTTPS_GET_URL + accessToken,
                    JSON.toJSONString(hardware)), SnTicket.class);
        } catch (Exception e) {
            logger.error(hardware.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (snTicket == null) {
            throw new RuntimeException("snTicket is null.");
        }

        if (snTicket.getErrCode() != 0) {
            throw new RuntimeException(snTicket.getErrMsg());
        }

        return snTicket;
    }

}
