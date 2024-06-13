package com.example.demo.banner.service;

import com.example.demo.banner.api.BannerService;
import com.example.demo.banner.api.bo.Banner;
import com.example.demo.banner.dao.dataobject.BannerDO;
import com.example.demo.banner.dao.mapper.BannerMapper;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
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

        return count(BeanUtil.copy(banner, BannerDO.class));
    }

    @Override
    public List<Banner> listBanners(Banner banner) {
        if (banner == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(banner, BannerDO.class)), Banner.class);
    }

    @Override
    public Banner getBanner(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getBanner(new BigInteger(id));
    }

    @Override
    public Banner getBanner(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(get(new BannerDO(id)), Banner.class);
    }

    @Override
    public Banner insertBanner(@NotNull Banner banner, @NotBlank String creator) {
        BannerDO bannerDO = BeanUtil.copy(banner, BannerDO.class);
        bannerDO.setCreator(creator);

        try {
            bannerMapper.insert(bannerDO);
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        banner.setId(bannerDO.getId());

        return banner;
    }

    @Override
    public Banner updateBanner(@NotNull BigInteger id, @NotNull Banner banner,
                               @NotBlank String modifier) {
        banner.setId(id);

        BannerDO bannerDO = BeanUtil.copy(banner, BannerDO.class);
        bannerDO.setModifier(modifier);

        try {
            if (bannerMapper.update(bannerDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }
        return banner;
    }

    @Override
    public Banner deleteBanner(@NotNull BigInteger id, @NotBlank String modifier) {
        BannerDO bannerDO = new BannerDO();
        bannerDO.setId(id);
        bannerDO.setModifier(modifier);

        try {
            bannerMapper.delete(bannerDO);
        } catch (Exception e) {
            logger.error(bannerDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(bannerDO, Banner.class);
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
