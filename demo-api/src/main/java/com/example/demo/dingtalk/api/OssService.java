package com.example.demo.dingtalk.api;

import java.util.Map;

public interface OssService {

    void putObject(String resourceUrl, Map<String, String> headers, String fileUrl);

}
