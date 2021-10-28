package com.chen.controller;

import com.chen.service.ArticleService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chen.vo.params.PageParams;;
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
    public Result listArticle(@RequestBody PageParams pageParams){
////        ArticleVo页面接收数据
//        Result articles=articleService.listArticlesPage(pageParams);
//        System.out.println(articles);
//        我们这里要传入数据给Result,数据要从service层里获取
        return articleService.listArticlesPage(pageParams);
    }
}
