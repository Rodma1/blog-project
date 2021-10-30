package com.chen.service;

import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.pojo.Article;

public interface TreadService {
//    阅读更新操作需要
    void updateViewCount(ArticleMapper articleMapper, Article article);
}
