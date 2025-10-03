package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.ArticleCategory;
import com.lblog.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ArticleCategoryApi {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    //添加文章分类
    @PostMapping("/addArticleCategory")
    public JsonResponseUtil<String> addArticleCategory(ArticleCategory articleCategory){
        articleCategoryService.addArticleCategory(articleCategory);

        return JsonResponseUtil.success();
    }

    //编辑文章分类
    @PostMapping("/editArticleCategory")
    public JsonResponseUtil<String> editArticleCategory(ArticleCategory articleCategory){
        articleCategoryService.editArticleCategory(articleCategory);

        return JsonResponseUtil.success();
    }

    //删除文章分类
    @DeleteMapping("/deleteArticleCategory")
    public JsonResponseUtil<String> deleteArticleCategory(Long articleCategoryId){
        articleCategoryService.deleteArticleCategory(articleCategoryId);

        return JsonResponseUtil.success();
    }

    //获取文章分类列表
    @GetMapping("/getArticleCategoryList")
    public JsonResponseUtil<PageResultUtil<ArticleCategory>> getArticleCategoryList(@RequestParam(defaultValue = "1") Integer startPage,
                                                                                    @RequestParam(defaultValue = "10") Integer size){
        PageResultUtil<ArticleCategory> articleCategoryList = articleCategoryService.getArticleCategoryList(startPage, size);

        return JsonResponseUtil.success(articleCategoryList);
    }

    //获取文章分类详情
    @GetMapping("/getArticleCategoryDetail")
    public JsonResponseUtil<ArticleCategory> getArticleCategoryDetail(Long articleCategoryId){
        ArticleCategory articleCategoryDetail = articleCategoryService.getArticleCategoryDetail(articleCategoryId);

        return JsonResponseUtil.success(articleCategoryDetail);
    }

    //获取文章分类数量
    @GetMapping("getArticleCategoryTotal")
    public JsonResponseUtil<Integer> getArticleCategoryTotal(){
        Integer articleCategoryTotal = articleCategoryService.getArticleCategoryTotal();

        return JsonResponseUtil.success(articleCategoryTotal);
    }
}
