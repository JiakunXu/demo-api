package com.example.demo.banner.service;

import com.example.demo.banner.api.BannerService;
import com.example.demo.banner.dao.mapper.BannerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerMapper        bannerMapper;

}
