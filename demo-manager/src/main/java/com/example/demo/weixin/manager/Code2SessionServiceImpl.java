package com.example.demo.weixin.manager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.Code2SessionService;
import com.example.demo.weixin.api.bo.sns.Session;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appid, String secret, String jsCode) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid 不能为空.");
        }

        if (StringUtils.isBlank(secret)) {
            throw new RuntimeException("secret 不能为空.");
        }

        if (StringUtils.isBlank(jsCode)) {
            throw new RuntimeException("js_code 不能为空.");
        }

        StringBuilder sb = new StringBuilder(Code2SessionService.HTTPS_CODE_2_SESSION_URL);
        sb.append("&appid=").append(appid).append("&secret=").append(secret).append("&js_code=")
            .append(jsCode);

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

        Integer errCode = session.getErrCode();
        if (errCode != null) {
            logger.error(session.toString());

            throw new RuntimeException(session.getErrMsg());
        }

        return session;
    }

}
