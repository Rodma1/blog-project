package com.chen.controller;


import com.chen.service.CommentsService;
import com.chen.vo.Result;
import com.chen.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 设置评论的接口
     * @param commentParam
     * @request：post
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
