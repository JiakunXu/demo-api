package com.example.demo.corp.web;

import com.example.demo.corp.api.CorpRoleService;
import com.example.demo.framework.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/corp/role")
public class CorpRoleController extends BaseController {

    @Autowired
    private CorpRoleService corpRoleService;

}
