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

    Integer deleteArticle(Long articleId);

    List<Article> getArticleList(@Param("startNum") Integer startNum,
                                 @Param("size") Integer size);

    Article getArticleDetail(Long articleId);

    Integer getArticleTotal();

    Integer editArticleReadCount(@Param("articleId") Long articleId,
                                 @Param("readCount") Integer readCount);
}
