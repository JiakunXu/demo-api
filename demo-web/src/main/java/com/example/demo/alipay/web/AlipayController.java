package com.example.demo.alipay.web;

import com.example.demo.framework.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/alipay")
public class AlipayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> parameters = new HashMap<>();
        for (Map.Entry<String, String[]> map : request.getParameterMap().entrySet()) {
            StringBuilder sb = new StringBuilder();
            for (String value : map.getValue()) {
                if (StringUtils.isNotBlank(sb)) {
                    sb.append(",");
                }
                sb.append(value);
            }
            parameters.put(map.getKey(), sb.toString());
        }

        return "success";
    }

}
