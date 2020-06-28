package com.example.demo.api.file;

import com.example.demo.api.file.bo.File;

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
