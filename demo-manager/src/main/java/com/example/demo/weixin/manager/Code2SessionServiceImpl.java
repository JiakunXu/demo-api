package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.Code2SessionService;
import com.example.demo.weixin.api.bo.sns.Session;
import com.example.demo.framework.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appid, String secret, String jsCode) throws RuntimeException {
        Session session;

        try {
            session = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, appid, secret, jsCode)),
                Session.class);
        } catch (Exception e) {
            logger.error(appid + "&" + secret + "&" + jsCode, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (session == null) {
            throw new RuntimeException("session is null.");
        }

        if (session.getErrCode() != 0) {
            logger.error(session.toString());

            throw new RuntimeException(session.getErrMsg());
        }

        return session;
    }

}
