package com.example.demo.article.service;

import com.example.demo.article.api.ArticleService;
import com.example.demo.article.api.bo.Article;
import com.example.demo.article.dao.dataobject.ArticleDO;
import com.example.demo.article.dao.mapper.ArticleMapper;
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
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private ArticleMapper       articleMapper;

    @Override
    public int countArticle(Article article) {
        if (article == null) {
            return 0;
        }

        return count(BeanUtil.copy(article, ArticleDO.class));
    }

    @Override
    public List<Article> listArticles(Article article) {
        if (article == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(article, ArticleDO.class)), Article.class);
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

        return BeanUtil.copy(get(new ArticleDO(id)), Article.class);
    }

    @Override
    public Article insertArticle(@NotNull Article article, @NotBlank String creator) {
        ArticleDO articleDO = BeanUtil.copy(article, ArticleDO.class);
        articleDO.setCreator(creator);

        try {
            articleMapper.insert(articleDO);
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
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
            if (articleMapper.update(articleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
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
            articleMapper.delete(articleDO);
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(articleDO, Article.class);
    }

    private int count(ArticleDO articleDO) {
        try {
            return articleMapper.count(articleDO);
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
        }

        return 0;
    }

    private List<ArticleDO> list(ArticleDO articleDO) {
        try {
            return articleMapper.list(articleDO);
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
        }

        return null;
    }

    private ArticleDO get(ArticleDO articleDO) {
        try {
            return articleMapper.get(articleDO);
        } catch (Exception e) {
            logger.error(articleDO.toString(), e);
        }

        return null;
    }

}
