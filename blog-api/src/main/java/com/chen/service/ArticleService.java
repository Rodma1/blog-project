package com.chen.service;

import java.util.List;
import com.chen.vo.ArticleVo;
import com.chen.vo.Result;
import com.chen.vo.params.PageParams;

//设置获取文章列表数据接口
public interface ArticleService {
//    通过AriticleVo给的属性参数创建返回给前端的列表listArticlesPage方法数据,获取展示文章信息
   Result listArticlesPage(PageParams pageParams);
//   找到最热文章
   Result hotArticle(int limit);
   //   找到最新文章
   Result newArticles(int limit);
//   文章归档
   Result listArchives();

//   点击进入文章内容查看详情
   ArticleVo findArticleById(Long id);
}
