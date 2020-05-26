package com.example.demo.api.file;

import com.example.demo.api.file.bo.File;

import java.io.IOException;
import java.io.InputStream;

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
    File insertFile(String name, InputStream content, String contentType) throws IOException;

}
