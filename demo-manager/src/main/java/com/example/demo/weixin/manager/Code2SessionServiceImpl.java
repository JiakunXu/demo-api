package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.Code2SessionService;
import com.example.demo.weixin.api.bo.sns.Session;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class Code2SessionServiceImpl implements Code2SessionService {

    @Override
    public Session getSession(String appid, String secret, String jsCode) throws RuntimeException {
        Session session;

        try {
            session = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, appid, secret, jsCode)),
                Session.class);
        } catch (Exception e) {
            log.error("{},{},{}", appid, secret, jsCode, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (session == null) {
            throw new RuntimeException("session is null.");
        }

        if (session.getErrCode() != 0) {
            log.error("{}", session);
            throw new RuntimeException(session.getErrMsg());
        }

        return session;
    }

}
