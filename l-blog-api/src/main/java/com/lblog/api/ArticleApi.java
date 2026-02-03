package com.lblog.api;

import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.Article;
import com.lblog.dto.ArticleDto;
import com.lblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    //添加文章
    @PostMapping("/addArticle")
    public JsonResponseUtil<String> addArticle(@RequestBody Article article){
        articleService.addArticle(article);

        return JsonResponseUtil.success();
    }

    //编辑文章
    @PostMapping("/editArticle")
    public JsonResponseUtil<String> editArticle(@RequestBody Article article){
        articleService.editArticle(article);

        return JsonResponseUtil.success();
    }

    //删除文章
    @DeleteMapping("/deleteArticle")
    public JsonResponseUtil<String> deleteArticle(@RequestBody Map<String, List<Long>> articleId){
        articleService.deleteArticle(articleId);

        return JsonResponseUtil.success();
    }

    //获取文章列表
    @GetMapping("/getArticleList")
    public JsonResponseUtil<PageResultUtil<ArticleDto>> getArticleList(@RequestParam(defaultValue = "1") Integer page,
                                                                       Article article,
                                                                       String moudle){
        PageResultUtil<ArticleDto> articleList = articleService.getArticleList(page, article, moudle);

        return JsonResponseUtil.success(articleList);
    }

    //获取文章详情
    @GetMapping("/getArticleDetail")
    public JsonResponseUtil<ArticleDto> getArticleDetailDto(Long articleId){
        ArticleDto articleDetail = articleService.getArticleDetailDto(articleId);

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
