package com.example.demo.banner.api;

import com.example.demo.banner.api.bo.Banner;

import java.math.BigInteger;
import java.util.List;

public interface BannerService {

    long countBanner(Banner banner);

    List<Banner> listBanners(Banner banner);

    Banner getBanner(String id);

    Banner getBanner(BigInteger id);

    Banner insertBanner(Banner banner, String createBy);

    Banner updateBanner(BigInteger id, Banner banner, String updateBy);

    Banner deleteBanner(BigInteger id, String updateBy);

}
