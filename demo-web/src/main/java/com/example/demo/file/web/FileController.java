package com.example.demo.file.web;

import com.example.demo.file.api.FileService;
import com.example.demo.file.api.bo.File;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ObjectResponse<File> upload(@RequestParam MultipartFile file, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        String state = this.getParameter(request, "state");

        try (InputStream stream = file.getInputStream()) {
            File f = fileService.upload(file.getOriginalFilename(), file.getContentType(), stream);
            f.setState(state);

            return new ObjectResponse<>(f);
        }
    }

}
