package com.example.demo.log.api;

import com.example.demo.log.api.bo.LoginLog;

import java.math.BigInteger;
import java.util.List;

public interface LoginLogService {

    int countLoginLog(LoginLog loginLog);

    List<LoginLog> listLoginLogs(LoginLog loginLog);

    LoginLog getLoginLog(String id);

    LoginLog getLoginLog(BigInteger id);

    LoginLog insertLoginLog(LoginLog loginLog, String creator);

}
