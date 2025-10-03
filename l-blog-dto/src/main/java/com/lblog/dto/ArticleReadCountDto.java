package com.lblog.dto;

import com.lblog.domain.Article;

public class ArticleReadCountDto {
    private Long id;

    private Integer readCount;

    public ArticleReadCountDto(Long id, Integer readCount){
        this.id = id;
        this.readCount = readCount;
    }

    public static ArticleReadCountDto getArticleReadCount(Article article){
        return new ArticleReadCountDto(article.getId(), article.getReadCount());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
