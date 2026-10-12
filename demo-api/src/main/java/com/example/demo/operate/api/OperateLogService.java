package com.example.demo.operate.api;

import com.example.demo.operate.api.bo.OperateLog;

import java.math.BigInteger;
import java.util.List;

public interface OperateLogService {

    long countLog(OperateLog log);

    List<OperateLog> listLogs(OperateLog log);

    OperateLog getLog(String id);

    OperateLog getLog(BigInteger id);

    void insertLog(OperateLog log, String createBy);

}
