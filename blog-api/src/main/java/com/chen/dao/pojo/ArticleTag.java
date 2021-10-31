package com.chen.dao.pojo;

import lombok.Data;

/**
 * 获取文章标签表的信息
 */
@Data
public class ArticleTag {

    private Long id;
//文章id
    private Long articleId;
//标签id
    private Long tagId;
}