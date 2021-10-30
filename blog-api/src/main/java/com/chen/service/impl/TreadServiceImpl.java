package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.pojo.Article;
import com.chen.service.TreadService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 更新文章阅读量
 */
@Service
@Component
public class TreadServiceImpl implements TreadService {
//期望此操作在线程池执行，不会影响原有的主线程
    @Override
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {
//        获取阅读量
        int getViewcount=article.getViewCounts();
//        new一个文章对象
        Article articleUpdate=new Article();
//        阅读量加一
        articleUpdate.setViewCounts(getViewcount+1);
//        初始化mapper映射
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        对比id是否对应
        queryWrapper.eq(Article::getId,article.getId());
        //设置一个 为了在多线程环境下 线程安全
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());
        //update article set view_count=100 where view_count=99 and id=11
        articleMapper.update(articleUpdate,queryWrapper);
        try {
            //睡眠5秒 证明不会影响主线程的使用
            Thread.sleep(5000);
            System.out.println("更新完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
