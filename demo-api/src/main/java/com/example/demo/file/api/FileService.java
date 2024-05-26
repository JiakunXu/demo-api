package com.example.demo.file.api;

import com.example.demo.file.api.bo.File;

import java.io.InputStream;

/**
 * @author JiakunXu
 */
public interface FileService {

    File upload(String name, String contentType, InputStream content);

}
