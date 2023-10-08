package com.example.demo.dingtalk.manager;

import com.example.demo.dingtalk.api.OssService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service("ossService3")
public class OssServiceImpl implements OssService {

    @Override
    public void putObject(String resourceUrl, Map<String, String> headers, String fileUrl) {
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) new URL(resourceUrl).openConnection();

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (OutputStream out = connection.getOutputStream()) {
            IOUtils.copy(new URL(fileUrl), out);
            out.flush();
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("上传失败");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.close(connection);
        }
    }

}
