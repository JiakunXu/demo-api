package com.example.demo.banner.service;

import com.example.demo.banner.api.BannerService;
import com.example.demo.banner.api.bo.Banner;
import com.example.demo.banner.dao.dataobject.BannerDO;
import com.example.demo.banner.dao.mapper.BannerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerMapper        bannerMapper;

    @Override
    public int countBanner(Banner banner) {
        if (banner == null) {
            return 0;
        }

        return 0;
    }

    @Override
    public List<Banner> listBanners(Banner banner) {
        if (banner == null) {
            return null;
        }

        return List.of();
    }

    @Override
    public Banner getBanner(String id) {
        return null;
    }

    @Override
    public Banner getBanner(BigInteger id) {
        return null;
    }

    @Override
    public Banner insertBanner(Banner banner, String creator) {
        return null;
    }

    @Override
    public Banner updateBanner(BigInteger id, Banner banner, String modifier) {
        return null;
    }

    @Override
    public Banner deleteBanner(BigInteger id, String modifier) {
        return null;
    }

    private int count(BannerDO bannerDO) {
        try {
            return bannerMapper.count(bannerDO);
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
        }

        return 0;
    }

    private List<BannerDO> list(BannerDO bannerDO) {
        try {
            return bannerMapper.list(bannerDO);
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
        }

        return null;
    }

    private BannerDO get(BannerDO bannerDO) {
        try {
            return bannerMapper.get(bannerDO);
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
        }

        return null;
    }

}
