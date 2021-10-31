package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.CommentMapper;
import com.chen.dao.pojo.Comment;
import com.chen.service.CommentsService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现评论列表的获取
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Override
    public Result commentsByArticleId(Long articleId) {
//        定义mapper查询评论表里面的信息
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
//        查找对应的文章和层级为1的评论
        queryWrapper.eq(Comment:: getArticleId,articleId);
        queryWrapper.eq(Comment:: getLevel,1);
//        执行语句：select * from blog_comment where article_id=1 and level=1;
        List<Comment> comments=commentMapper.selectList(queryWrapper);
        return Result.success(comments);
    }
}
