package com.chen.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.dao.dos.Archives;
import com.chen.dao.pojo.Article;

import java.util.List;

//BaseMapper是mybatis-plus提供的，可以很快查询到article，然后可以很快的和数据库表自动关联
public interface ArticleMapper extends BaseMapper<Article>{
//    这里说一下我们继承leBaseMapper是因为这个类里面已经为我们提供了CRUD方法
//    获取文章的同一年月发布的数量
    List<Archives> listArchives();
//文章列表and文章归档功能
    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
