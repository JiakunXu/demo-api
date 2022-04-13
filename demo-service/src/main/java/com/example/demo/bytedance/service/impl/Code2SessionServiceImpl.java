package com.example.demo.bytedance.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.bytedance.Code2SessionService;
import com.example.demo.api.bytedance.ao.Session;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("code2SessionService")
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appId, String appSecret, String code) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid is null.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret is null.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code is null.");
        }

        StringBuilder sb = new StringBuilder(Code2SessionService.HTTPS_CODE_2_SESSION_URL);
        sb.append("?appid=").append(appId).append("&secret=").append(appSecret).append("&code=")
            .append(code);

        Session session = null;

        try {
            session = JSON.parseObject(HttpUtil.get(sb.toString()), Session.class);
        } catch (Exception e) {
            logger.error(sb.toString(), e);

            throw new RuntimeException("HttpUtil error.");
        }

        if (session == null) {
            throw new RuntimeException("session is null.");
        }

        String errCode = session.getErrCode();
        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(session.getErrMsg());
        }

        return session;
    }

}
