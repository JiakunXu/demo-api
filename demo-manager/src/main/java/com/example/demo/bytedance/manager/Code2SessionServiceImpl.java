package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.Code2SessionService;
import com.example.demo.bytedance.api.bo.session.Session;
import com.example.demo.framework.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.bytedance.manager.code2SessionService")
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appid, String secret, String code) throws RuntimeException {
        Session session;

        try {
            session = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_GET_URL, appid, secret, code)),
                Session.class);
        } catch (Exception e) {
            logger.error(appid + "&" + secret + "&" + code, e);
            throw new RuntimeException(e);
        }

        if (session == null) {
            throw new RuntimeException("session is null.");
        }

        if (session.getErrNo() != 0) {
            throw new RuntimeException(session.getErrTips());
        }

        return session;
    }

}
