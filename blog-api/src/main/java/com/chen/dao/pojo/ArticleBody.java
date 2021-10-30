package com.chen.dao.pojo;

import lombok.Data;

@Data
public class ArticleBody {
    private Long id;
//    内容
    private String content;
//    内容的html
    private String contentHtml;
//    文章id，通过这个id来和对应的文章表关联
    private Long articleId;
}
