package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.ArticleCategoryDao;
import com.lblog.domain.ArticleCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    //添加文章分类
    @Transactional
    public void addArticleCategory(ArticleCategory articleCategory){
        //判断分类名称是否合法和是否已存在
        String name = articleCategory.getName().trim();
        if(StringUtils.isBlank(name)){
            throw new ReturnException("文章分类名称不能为空！");
        }
        Long existArticleCategoryId = articleCategoryDao.getArticleCategoryId(name);
        if((existArticleCategoryId != null) && (existArticleCategoryId > 0)){
            throw new ReturnException("文章分类名称已存在！");
        }

        String description = articleCategory.getDescription().trim();
        Integer status = 1;
        Integer sortOrder = 0;
        Long addTime = Instant.now().toEpochMilli();
        articleCategory.setName(name);
        articleCategory.setDescription(description);
        articleCategory.setStatus(status);
        articleCategory.setSortOrder(sortOrder);
        articleCategory.setAddTime(addTime);
        articleCategoryDao.addArticleCategory(articleCategory);
    }

    //编辑文章分类
    @Transactional
    public void editArticleCategory(ArticleCategory articleCategory){
        //判断文章分类是否存在
        Long articleCategoryId = articleCategory.getId();
        ArticleCategory articleCategoryDetail = articleCategoryDao.getArticleCategoryDetail(articleCategoryId);
        if(articleCategoryDetail == null){
            throw new ReturnException("文章分类不存在！");
        }

        String name = articleCategory.getName().trim();
        String description = articleCategory.getDescription().trim();
        Integer status = articleCategory.getStatus();
        Integer sortOrder = articleCategory.getSortOrder();
        Long editTime = Instant.now().toEpochMilli();
        articleCategory.setName(name);
        articleCategory.setDescription(description);
        articleCategory.setStatus(status);
        articleCategory.setSortOrder(sortOrder);
        articleCategory.setEditTime(editTime);
        articleCategoryDao.editArticleCategory(articleCategory);
    }

    //删除文章分类
    @Transactional
    public void deleteArticleCategory(Long articleCategoryId){
        //判断分类ID
        if((articleCategoryId == null) || (articleCategoryId == 0)){
            throw new ReturnException("文章分类ID不能为空！");
        }

        articleCategoryDao.deleteArticleCategory(articleCategoryId);
    }

    //获取文章列表
    public PageResultUtil<ArticleCategory> getArticleCategoryList(Integer startPage, Integer size){
        //起始位置
        Integer startNum = (startPage-1) * size;
        //获取总数
        Integer total = articleCategoryDao.getArticleCategoryTotal();
        //查询列表
        List<ArticleCategory> articleCategoryList = articleCategoryDao.getArticleCategoryList(startNum, size);

        return new PageResultUtil<>(startPage, size, total, articleCategoryList);
    }

    //获取文章详情
    @Transactional
    public ArticleCategory getArticleCategoryDetail(Long articleCategoryId){
        //判断分类ID
        if((articleCategoryId == null) || (articleCategoryId == 0)){
            throw new ReturnException("文章分类ID不能为空！");
        }

        return articleCategoryDao.getArticleCategoryDetail(articleCategoryId);
    }

    //获取用户数量
    public Integer getArticleCategoryTotal(){
        return articleCategoryDao.getArticleCategoryTotal();
    }
}
