package com.lblog.dto;

import com.lblog.domain.Article;

public class ArticleDto {
    private Long id;

    private Long categoryId;

    private String title;

    private String content;

    private Integer readCount;

    private Integer status;

    private Integer sortOrder;

    private Long addTime;

    private Long editTime;

    //联表查询字段
    private String categoryName;

    public ArticleDto(){
    }

    public static ArticleDto articleDto(Article article,
                             String categoryName){
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setCategoryId(article.getCategoryId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setReadCount(article.getReadCount());
        articleDto.setStatus(article.getStatus());
        articleDto.setSortOrder(article.getSortOrder());
        articleDto.setAddTime(article.getAddTime());
        articleDto.setEditTime(article.getEditTime());
        articleDto.setCategoryName(categoryName);

        return articleDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
