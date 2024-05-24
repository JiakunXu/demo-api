package com.example.demo.log.web;

import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.log.api.LoginLogService;
import com.example.demo.log.api.bo.LoginLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monitor/log/login")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    @PreAuthorize("hasAuthority('monitor:log:crud')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<LoginLog> list(HttpServletRequest request, HttpServletResponse response) {
        LoginLog loginLog = this.getParameter(request, new LoginLog());

        int count = loginLogService.countLoginLog(loginLog);

        if (count == 0) {
            return new ListResponse<>(0, null);
        }

        return new ListResponse<>(count, loginLogService.listLoginLogs(loginLog));
    }

    @PreAuthorize("hasAuthority('monitor:log:crud')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<LoginLog> get(HttpServletRequest request, HttpServletResponse response) {
        String id = this.getParameter(request, "id");
        return new ObjectResponse<>(loginLogService.getLoginLog(id));
    }

}
