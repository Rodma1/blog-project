package com.chen.controller;


import com.chen.service.CommentsService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取评论信息
 * result :json
 */
//返回json数据
@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService  commentsService;
//    创建子路由
    @GetMapping ("article/{id}")
    public Result comments(@PathVariable("id") Long  articleId){
        return commentsService.commentsByArticleId(articleId);
    }
}
