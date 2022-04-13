package com.example.demo.weixin.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.Code2SessionService;
import com.example.demo.weixin.api.ao.sns.Session;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class Code2SessionServiceImpl implements Code2SessionService {

    private static final Logger logger = LoggerFactory.getLogger(Code2SessionServiceImpl.class);

    @Override
    public Session getSession(String appId, String appSecret, String code) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret 公众号的appsecret 不能为空.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code code参数 不能为空.");
        }

        StringBuilder sb = new StringBuilder(Code2SessionService.HTTPS_CODE_2_SESSION_URL);
        sb.append("&appid=").append(appId).append("&secret=").append(appSecret).append("&js_code=")
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
        if (StringUtils.isNotBlank(errCode)) {
            logger.error(JSON.toJSONString(session));

            throw new RuntimeException(session.getErrMsg());
        }

        return session;
    }

}
