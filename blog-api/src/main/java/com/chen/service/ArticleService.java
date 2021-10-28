package com.chen.service;

import java.util.List;
import com.chen.vo.ArticleVo;
import com.chen.vo.Result;
import com.chen.vo.params.PageParams;

//设置获取文章列表数据接口
public interface ArticleService {
//    通过AriticleVo给的属性参数创建返回给前端的列表listArticlesPage方法数据
   Result listArticlesPage(PageParams pageParams);
}
