package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.Article;
import com.lblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    //添加文章
    @PostMapping("/addArticle")
    public JsonResponseUtil<String> addArticle(Article article){
        articleService.addArticle(article);

        return JsonResponseUtil.success();
    }

    //编辑文章
    @PostMapping("/editArticle")
    public JsonResponseUtil<String> editArticle(Article article){
        articleService.editArticle(article);

        return JsonResponseUtil.success();
    }

    //删除文章
    @DeleteMapping("/deleteArticle")
    public JsonResponseUtil<String> deleteArticle(Long articleId){
        articleService.deleteArticle(articleId);

        return JsonResponseUtil.success();
    }

    //获取文章列表
    @GetMapping("/getArticleList")
    public JsonResponseUtil<PageResultUtil<Article>> getArticleList(@RequestParam(defaultValue = "1") Integer startPage,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    Article article){
        PageResultUtil<Article> articleList = articleService.getArticleList(startPage, size, article);

        return JsonResponseUtil.success(articleList);
    }

    //获取文章详情
    @GetMapping("/getArticleDetail")
    public JsonResponseUtil<Article> getArticleDetail(Long articleId){
        Article articleDetail = articleService.getArticleDetail(articleId);

        return JsonResponseUtil.success(articleDetail);
    }

    //获取文章数量
    @GetMapping("/getArticleTotal")
    public JsonResponseUtil<Integer> getArticleTotal(Article article){
        Integer articleTotal = articleService.getArticleTotal(article);

        return JsonResponseUtil.success(articleTotal);
    }

    //编辑文章阅读量
    @PostMapping("/editArticleReadCount")
    public JsonResponseUtil<String> editArticleReadCount(Long articleId, Integer readCount){
        articleService.editArticleReadCount(articleId, readCount);

        return JsonResponseUtil.success();
    }
}
