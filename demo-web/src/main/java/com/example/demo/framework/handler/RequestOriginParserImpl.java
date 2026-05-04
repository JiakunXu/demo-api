package com.example.demo.framework.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestOriginParserImpl implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

}
