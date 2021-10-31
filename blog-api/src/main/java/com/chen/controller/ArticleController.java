package com.chen.controller;

import com.chen.common.aop.LogAnnotation;
import com.chen.service.ArticleService;
import com.chen.vo.ArticleVo;
import com.chen.vo.Result;
import com.chen.vo.params.ArticleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import com.chen.vo.params.PageParams;
//通过json数据进行交互
@RestController
//设置映射路径（通俗理解就是路由）
@RequestMapping("articles")
public class ArticleController {
    /*
    首页：文章列表
    路由:/articles
    参数：pageParams页数
    返回值：json数据
    请求：post
     */
    @Autowired
    private ArticleService articleService;

    @PostMapping//post请求
//    加上注解，代表要对此接口记录日志，module是模块名称，operation是操作名称
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
////        ArticleVo页面接收数据
//        Result articles=articleService.listArticlesPage(pageParams);
//        System.out.println(articles);
//        我们这里要传入数据给Result,数据要从service层里获取
        return articleService.listArticlesPage(pageParams);
    }
//    最热文章路由
    @PostMapping("hot")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);
    }
//    最新文章

    @PostMapping("new")
    public Result newArticle(){
        int limit=5;
        return articleService.newArticles(limit);
    }
//    文章归档
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章内容
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        ArticleVo articleVo= articleService.findArticleById(id);
        return Result.success(articleVo);
    }
    /**
     * 提交文章接口
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
//        articleParam数据存入到数据库后在返回
        return articleService.publish(articleParam);

    }
}
