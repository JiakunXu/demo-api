package com.example.demo.privacy.web;

import com.example.demo.dict.api.DictDataService;
import com.example.demo.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/privacy")
public class PrivacyController extends BaseController {

    @Autowired
    private DictDataService dictDataService;

}
