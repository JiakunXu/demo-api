package com.example.demo.file.api;

import com.example.demo.file.api.bo.File;

import java.io.IOException;

/**
 * @author JiakunXu
 */
public interface FileService {

    /**
     *
     * @param name
     * @param content
     * @param contentType
     * @return
     * @throws IOException
     */
    File insertFile(String name, byte[] content, String contentType) throws IOException;

}
