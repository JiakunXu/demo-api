package com.example.demo.log.web;

import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.log.api.OperLogService;
import com.example.demo.log.api.bo.OperLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/monitor/log/oper")
public class OperLogController extends BaseController {

    @Autowired
    private OperLogService operLogService;

    @PreAuthorize("hasAuthority('monitor:log:crud')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<OperLog> list(HttpServletRequest request, HttpServletResponse response) {
        OperLog operLog = this.getParameter(request, new OperLog());

        String corpId = this.getParameter(request, "corpId");
        if (StringUtils.isNotBlank(corpId)) {
            operLog.setCorpId(new BigInteger(corpId));
        }

        int count = operLogService.countOperLog(operLog);

        if (count == 0) {
            return new ListResponse<>(0, null);
        }

        return new ListResponse<>(count, operLogService.listOperLogs(operLog));
    }

    @PreAuthorize("hasAuthority('monitor:log:crud')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<OperLog> get(HttpServletRequest request, HttpServletResponse response) {
        String id = this.getParameter(request, "id");
        return new ObjectResponse<>(operLogService.getOperLog(id));
    }

}
