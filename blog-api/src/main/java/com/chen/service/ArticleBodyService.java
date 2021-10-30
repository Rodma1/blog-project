package com.chen.service;


import com.chen.vo.ArticleBodyVo;

//获取文章内容接口
public interface ArticleBodyService {
//    通过文章表id来获取对应的文章内容
    ArticleBodyVo findArticleBody(long articleId);
}
