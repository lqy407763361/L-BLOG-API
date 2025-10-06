package com.lblog.dao;

import com.lblog.domain.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleCategoryDao {
    Long getArticleCategoryId(String name);

    Integer addArticleCategory(ArticleCategory articleCategory);

    Integer editArticleCategory(ArticleCategory articleCategory);

    Integer deleteArticleCategory(@Param("id") Long articleCategoryId);

    List<ArticleCategory> getArticleCategoryList(@Param("startNum") Integer startNum,
                                                 @Param("size") Integer size,
                                                 @Param("articleCategory") ArticleCategory articleCategory);

    ArticleCategory getArticleCategoryDetail(@Param("id") Long articleCategoryId);

    Integer getArticleCategoryTotal(ArticleCategory articleCategory);
}
