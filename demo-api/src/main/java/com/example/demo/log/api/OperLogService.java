package com.example.demo.log.api;

import com.example.demo.log.api.bo.OperLog;

import java.util.List;

public interface OperLogService {

    int countOperLog(OperLog operLog);

    List<OperLog> listOperLogs(OperLog operLog);

    OperLog getOperLog(String id);

    OperLog insertOperLog(OperLog operLog, String creator);

}
