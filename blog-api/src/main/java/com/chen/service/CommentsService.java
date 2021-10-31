package com.chen.service;

import com.chen.vo.Result;

public interface CommentsService {
//    根据文章id查询评论列表
    Result commentsByArticleId(Long articleId);
}
