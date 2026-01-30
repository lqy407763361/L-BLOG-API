package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.ArticleCategoryDao;
import com.lblog.dao.ArticleDao;
import com.lblog.domain.Article;
import com.lblog.domain.ArticleCategory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Autowired
    private ArticleDao articleDao;

    //添加文章分类
    @Transactional
    public void addArticleCategory(ArticleCategory articleCategory){
        //判断分类名称是否合法和是否已存在
        if(StringUtils.isBlank(articleCategory.getName())){
            throw new ReturnException("文章分类名称不能为空！");
        }
        String name = articleCategory.getName().trim();
        Long existArticleCategoryId = articleCategoryDao.getArticleCategoryId(name);
        if((existArticleCategoryId != null) && (existArticleCategoryId > 0)){
            throw new ReturnException("文章分类名称已存在！");
        }

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(articleCategory.getDescription())){
            description = articleCategory.getDescription().trim();
        }

        Integer status = 1;
        Integer sortOrder = 0;
        Long addTime = Instant.now().getEpochSecond();
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

        //判断分类名称是否合法和是否已存在
        if(StringUtils.isBlank(articleCategory.getName())){
            throw new ReturnException("文章分类名称不能为空！");
        }
        String name = articleCategory.getName().trim();

        //内容过滤
        String description = "";
        if(!StringUtils.isBlank(articleCategory.getDescription())){
            description = articleCategory.getDescription().trim();
        }

        Integer status = articleCategory.getStatus();
        Integer sortOrder = articleCategory.getSortOrder();
        Long editTime = Instant.now().getEpochSecond();
        articleCategory.setName(name);
        articleCategory.setDescription(description);
        articleCategory.setStatus(status);
        articleCategory.setSortOrder(sortOrder);
        articleCategory.setEditTime(editTime);
        articleCategoryDao.editArticleCategory(articleCategory);
    }

    //删除文章分类
    @Transactional
    public void deleteArticleCategory(Map<String, List<Long>> articleCategoryId){
        //判断文章分类ID
        if((articleCategoryId == null) || articleCategoryId.isEmpty()){
            throw new ReturnException("文章分类ID不能为空！");
        }

        //判断该分类下属是否存在文章
        List<Long> categoryIdList = articleCategoryId.get("id");
        for(Long categoryId : categoryIdList){
            Article article = new Article();
            article.setCategoryId(categoryId);
            Integer articleTotal = articleDao.getArticleTotal(article);
            if((articleTotal != null) && (articleTotal > 0)){
                throw new ReturnException("该分类下存在文章！");
            }
        }

        List<Long> articleCategoryIdList = articleCategoryId.get("id");
        articleCategoryDao.deleteArticleCategory(articleCategoryIdList);
    }

    //获取文章分类列表
    @Transactional(readOnly = true)
    public PageResultUtil<ArticleCategory> getArticleCategoryList(Integer page, Integer size, ArticleCategory articleCategory){
        //起始位置
        Integer startNum = (page-1) * size;
        //获取总数
        Integer total = articleCategoryDao.getArticleCategoryTotal(articleCategory);
        //查询列表
        List<ArticleCategory> articleCategoryList = articleCategoryDao.getArticleCategoryList(startNum, size, articleCategory);

        return new PageResultUtil<>(page, size, total, articleCategoryList);
    }

    //获取文章分类详情
    @Transactional(readOnly = true)
    public ArticleCategory getArticleCategoryDetail(Long articleCategoryId){
        //判断分类ID
        if((articleCategoryId == null) || (articleCategoryId == 0)){
            throw new ReturnException("文章分类ID不能为空！");
        }

        return articleCategoryDao.getArticleCategoryDetail(articleCategoryId);
    }

    //获取文章分类数量
    @Transactional(readOnly = true)
    public Integer getArticleCategoryTotal(ArticleCategory articleCategory){
        return articleCategoryDao.getArticleCategoryTotal(articleCategory);
    }
}
