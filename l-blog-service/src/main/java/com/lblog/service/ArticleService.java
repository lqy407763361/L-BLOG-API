package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.ArticleDao;
import com.lblog.domain.Article;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    //添加文章
    @Transactional
    public void addArticle(Article article){
        //判断名称是否合法和是否已存在
        String title = article.getTitle().trim();
        if(StringUtils.isBlank(title)){
            throw new ReturnException("文章名称不能为空！");
        }
        Long existArticleId = articleDao.getArticleId(title);
        if((existArticleId != null) && (existArticleId > 0)){
            throw new ReturnException("文章名称已存在！");
        }

        Long articleCategoryId = article.getCategoryId();
        String content = article.getContent().trim();
        Integer status = 1;
        Integer sortOrder = 0;
        Long addTime = Instant.now().toEpochMilli();
        article.setCategoryId(articleCategoryId);
        article.setTitle(title);
        article.setContent(content);
        article.setStatus(status);
        article.setSortOrder(sortOrder);
        article.setAddTime(addTime);
        articleDao.addArticle(article);
    }

    //编辑文章
    @Transactional
    public void editArticle(Article article){
        //判断文章是否存在
        Long articleId = article.getId();
        Article articleDetail = articleDao.getArticleDetail(articleId);
        if(articleDetail == null){
            throw new ReturnException("文章不存在！");
        }

        Long articleCategoryId = article.getCategoryId();
        String title = article.getTitle().trim();
        String content = article.getContent().trim();
        Integer status = 1;
        Integer sortOrder = 0;
        Long editTime = Instant.now().toEpochMilli();
        article.setCategoryId(articleCategoryId);
        article.setTitle(title);
        article.setContent(content);
        article.setStatus(status);
        article.setSortOrder(sortOrder);
        article.setEditTime(editTime);
        articleDao.editArticle(article);
    }

    //删除文章
    @Transactional
    public void deleteArticle(Long articleId){
        //判断分类ID
        if((articleId == null) || (articleId == 0)){
            throw new ReturnException("文章分类ID不能为空！");
        }

        articleDao.deleteArticle(articleId);
    }

    //获取文章列表
    public PageResultUtil<Article> getArticleList(Integer startPage, Integer size, Article article){
        //起始位置
        Integer startNum = (startPage-1) * size;
        //获取总数
        Integer total = articleDao.getArticleTotal(article);
        //查询列表
        List<Article> articleList = articleDao.getArticleList(startNum, size, article);

        return new PageResultUtil<>(startPage, size, total, articleList);
    }

    //获取文章详情
    @Transactional
    public Article getArticleDetail(Long articleId){
        //判断分类ID
        if((articleId == null) || (articleId == 0)){
            throw new ReturnException("文章分类ID不能为空！");
        }

        return articleDao.getArticleDetail(articleId);
    }

    //获取文章数量
    public Integer getArticleTotal(Article article){
        return articleDao.getArticleTotal(article);
    }

    //编辑文章阅读量
    public void editArticleReadCount(Long articleId, Integer readCount){
        articleDao.editArticleReadCount(articleId, readCount);
    }
}
