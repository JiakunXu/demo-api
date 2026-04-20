package com.example.demo.alipay.web;

import com.example.demo.framework.web.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/alipay")
public class AlipayController extends BaseController {

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        try {

        } catch (RuntimeException e) {
            log.error("{}", request, e);
            return "fail";
        }

        return "success";
    }

    private Map<String, String> getParameters(Map<String, String[]> parameterMap) {
        Map<String, String> parameters = new HashMap<>();
        for (Map.Entry<String, String[]> map : parameterMap.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String value : map.getValue()) {
                if (StringUtils.isNotBlank(sb)) {
                    sb.append(",");
                }
                sb.append(value);
            }
            parameters.put(map.getKey(), sb.toString());
        }

        return parameters;
    }

}
