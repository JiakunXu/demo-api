package com.example.demo.corp.service;

import com.example.demo.corp.api.CorpRoleService;
import com.example.demo.corp.dao.mapper.CorpRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorpRoleServiceImpl implements CorpRoleService {

    private static final Logger logger = LoggerFactory.getLogger(CorpRoleServiceImpl.class);

    @Autowired
    private CorpRoleMapper      corpRoleMapper;

}
