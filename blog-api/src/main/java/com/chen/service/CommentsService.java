package com.chen.service;

import com.chen.vo.Result;
import com.chen.vo.params.CommentParam;

public interface CommentsService {
//    根据文章id查询评论列表
    Result commentsByArticleId(Long articleId);
//    评论功能实现
    Result comment(CommentParam commentParam);
}
