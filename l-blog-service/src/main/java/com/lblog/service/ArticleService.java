package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.ArticleDao;
import com.lblog.domain.Article;
import com.lblog.dto.ArticleDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    //添加文章
    @Transactional
    public void addArticle(Article article){
        //判断名称是否合法和是否已存在
        if(StringUtils.isBlank(article.getTitle())){
            throw new ReturnException("文章名称不能为空！");
        }
        String title = article.getTitle().trim();
        Long existArticleId = articleDao.getArticleId(title);
        if((existArticleId != null) && (existArticleId > 0)){
            throw new ReturnException("文章名称已存在！");
        }

        //内容过滤
        String content = "";
        if(!StringUtils.isBlank(article.getContent())){
            content = article.getContent().trim();
        }

        Long articleCategoryId = article.getCategoryId();
        Integer status = article.getStatus();
        Integer sortOrder = article.getSortOrder();
        Long addTime = Instant.now().getEpochSecond();
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

        //标题过滤
        if(StringUtils.isBlank(article.getTitle())){
            throw new ReturnException("文章名称不能为空！");
        }
        String title = article.getTitle().trim();

        //内容过滤
        String content = "";
        if(!StringUtils.isBlank(article.getContent())){
            content = article.getContent().trim();
        }

        Long articleCategoryId = article.getCategoryId();
        Integer status = article.getStatus();
        Integer sortOrder = article.getSortOrder();
        Long editTime = Instant.now().getEpochSecond();
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
    public void deleteArticle(Map<String, List<Long>> articleId){
        //判断文章ID
        if((articleId == null) || articleId.isEmpty()){
            throw new ReturnException("文章ID不能为空！");
        }

        List<Long> articleIdList = articleId.get("articleId");
        articleDao.deleteArticle(articleIdList);
    }

    //获取文章列表
    public PageResultUtil<ArticleDto> getArticleList(Integer page, Integer size, Article article){
        //起始位置
        Integer startNum = (page-1) * size;
        //获取总数
        Integer total = articleDao.getArticleTotal(article);
        //查询列表
        List<ArticleDto> articleList = articleDao.getArticleList(startNum, size, article);

        return new PageResultUtil<>(page, size, total, articleList);
    }

    /**
     * 获取文章详情
     * 用于内部查询，编辑
     * */
    @Transactional
    public Article getArticleDetail(Long articleId){
        //判断文章ID
        if((articleId == null) || (articleId == 0)){
            throw new ReturnException("文章ID不能为空！");
        }

        return articleDao.getArticleDetail(articleId);
    }

    /**
     * 获取文章详情
     * 重新组装展示字段
     * */
    @Transactional
    public ArticleDto getArticleDetailDto(Long articleId){
        //判断文章ID
        if((articleId == null) || (articleId == 0)){
            throw new ReturnException("文章ID不能为空！");
        }

        return articleDao.getArticleDetailDto(articleId);
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
