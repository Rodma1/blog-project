package com.chen.service;

import com.chen.vo.TagVo;

import java.util.List;

public interface TagService {
//    通过文章id找到对应的标签信息
    List<TagVo> findTagsByArticleId(Long articleId);
}
