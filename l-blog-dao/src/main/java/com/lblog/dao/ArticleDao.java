package com.lblog.dao;

import com.lblog.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {
    Long getArticleId(String title);

    Integer addArticle(Article article);

    Integer editArticle(Article article);

    Integer deleteArticle(@Param("id") Long articleId);

    List<Article> getArticleList(@Param("startNum") Integer startNum,
                                 @Param("size") Integer size,
                                 @Param("article") Article article);

    Article getArticleDetail(@Param("id") Long articleId);

    Integer getArticleTotal(Article article);

    Integer editArticleReadCount(@Param("id") Long articleId,
                                 @Param("readCount") Integer readCount);
}
