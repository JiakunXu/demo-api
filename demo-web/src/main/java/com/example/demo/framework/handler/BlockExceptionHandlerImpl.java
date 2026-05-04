package com.example.demo.framework.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class BlockExceptionHandlerImpl implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       String resourceName, BlockException e) throws Exception {
        response.getWriter()
            .write(JSON.toJSONString(new ExceptionResponse(Constants.TOO_MANY_REQUESTS,
                "Blocked by Sentinel (flow limiting)")));
    }

}
