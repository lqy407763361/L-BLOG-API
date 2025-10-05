package com.lblog.dto;

import com.lblog.domain.Article;

public class ArticleReadCountDto {
    private Long id;

    private Integer readCount;

    private ArticleReadCountDto(){
    }

    public static ArticleReadCountDto articleReadCountDto(Article article){
        ArticleReadCountDto articleReadCountDto = new ArticleReadCountDto();
        articleReadCountDto.setId(article.getId());
        articleReadCountDto.setReadCount(article.getReadCount());

        return articleReadCountDto;
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
