package com.example.demo.article.service;

import com.example.demo.article.api.ArticleService;
import com.example.demo.article.api.bo.Article;
import com.example.demo.article.dao.dataobject.ArticleDO;
import com.example.demo.article.dao.mapper.ArticleMapper;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleDO>
                                implements ArticleService {

    @Override
    public int countArticle(Article article) {
        if (article == null) {
            return 0;
        }

        return this.count(BeanUtil.copy(article, ArticleDO.class));
    }

    @Override
    public List<Article> listArticles(Article article) {
        if (article == null) {
            return null;
        }

        return BeanUtil.copy(this.list(BeanUtil.copy(article, ArticleDO.class)), Article.class);
    }

    @Override
    public Article getArticle(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return getArticle(new BigInteger(id));
    }

    @Override
    public Article getArticle(BigInteger id) {
        if (id == null) {
            return null;
        }

        return BeanUtil.copy(this.get(new ArticleDO(id)), Article.class);
    }

    @Override
    public Article insertArticle(@NotNull Article article, @NotBlank String creator) {
        ArticleDO articleDO = BeanUtil.copy(article, ArticleDO.class);
        articleDO.setCreator(creator);

        try {
            this.insert(articleDO);
        } catch (Exception e) {
            log.error("{}", articleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        article.setId(articleDO.getId());

        return article;
    }

    @Override
    public Article updateArticle(@NotNull BigInteger id, @NotNull Article article,
                                 @NotBlank String modifier) {
        article.setId(id);

        ArticleDO articleDO = BeanUtil.copy(article, ArticleDO.class);
        articleDO.setModifier(modifier);

        try {
            if (this.update(articleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", articleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return article;
    }

    @Override
    public Article deleteArticle(@NotNull BigInteger id, @NotBlank String modifier) {
        ArticleDO articleDO = new ArticleDO();
        articleDO.setId(id);
        articleDO.setModifier(modifier);

        try {
            this.delete(articleDO);
        } catch (Exception e) {
            log.error("{}", articleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(articleDO, Article.class);
    }

}
