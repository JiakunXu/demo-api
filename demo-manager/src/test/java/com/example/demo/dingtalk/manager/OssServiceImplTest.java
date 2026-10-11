package com.example.demo.dingtalk.manager;

import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OssServiceImplTest {
    @TempDir
    Path directory;

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void uploadsFileWithOptionalHeadersAndDisconnects(boolean headers) throws Exception {
        Path source = directory.resolve("payload.bin");
        Files.write(source, new byte[] {1, 2, 3});
        URL fileUrl = source.toUri().toURL();
        URI resource = mock(URI.class);
        URI file = mock(URI.class);
        URL resourceUrl = mock(URL.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        var output = new ByteArrayOutputStream();
        when(resource.toURL()).thenReturn(resourceUrl);
        when(file.toURL()).thenReturn(fileUrl);
        when(resourceUrl.openConnection()).thenReturn(connection);
        when(connection.getOutputStream()).thenReturn(output);
        when(connection.getResponseCode()).thenReturn(200);
        try (MockedStatic<URI> uris = mockStatic(URI.class, CALLS_REAL_METHODS)) {
            uris.when(() -> URI.create("https://upload.example.com")).thenReturn(resource);
            uris.when(() -> URI.create("file:test")).thenReturn(file);
            new OssServiceImpl().putObject("https://upload.example.com", headers ? Map.of("x-token", "token") : null, "file:test");
            assertThat(output.toByteArray()).containsExactly(1, 2, 3);
            verify(connection).setRequestMethod("PUT");
            verify(connection).setDoOutput(true);
            verify(connection).setUseCaches(false);
            verify(connection).setReadTimeout(10000);
            verify(connection).setConnectTimeout(10000);
            verify(connection).connect();
            verify(connection).disconnect();
            if (headers) verify(connection).setRequestProperty("x-token", "token");
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void rejectedOrFailedUploadDisconnects(boolean rejected) throws Exception {
        Path source = directory.resolve("payload.bin");
        Files.write(source, new byte[] {1});
        URL fileUrl = source.toUri().toURL();
        URI resource = mock(URI.class);
        URI file = mock(URI.class);
        URL resourceUrl = mock(URL.class);
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(resource.toURL()).thenReturn(resourceUrl);
        when(file.toURL()).thenReturn(fileUrl);
        when(resourceUrl.openConnection()).thenReturn(connection);
        IOException failure = new IOException("write failed");
        if (rejected) {
            when(connection.getOutputStream()).thenReturn(new ByteArrayOutputStream());
            when(connection.getResponseCode()).thenReturn(403);
        } else when(connection.getOutputStream()).thenThrow(failure);
        try (MockedStatic<URI> uris = mockStatic(URI.class, CALLS_REAL_METHODS)) {
            uris.when(() -> URI.create("https://upload.example.com")).thenReturn(resource);
            uris.when(() -> URI.create("file:test")).thenReturn(file);
            var assertion = assertThatThrownBy(() -> new OssServiceImpl().putObject("https://upload.example.com", null, "file:test"));
            if (rejected) assertion.hasMessage("上传失败");
            else assertion.hasCause(failure);
            verify(connection).disconnect();
        }
    }
}
