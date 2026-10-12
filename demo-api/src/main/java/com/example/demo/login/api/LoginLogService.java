package com.example.demo.login.api;

import com.example.demo.login.api.bo.LoginLog;

import java.math.BigInteger;
import java.util.List;

public interface LoginLogService {

    long countLog(LoginLog log);

    List<LoginLog> listLogs(LoginLog log);

    LoginLog getLog(String id);

    LoginLog getLog(BigInteger id);

    void insertLog(LoginLog log, String createBy);

}
