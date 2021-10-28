package com.chen.vo;
//设置返回给前端的参数
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
//    设置返回给前端需要的参数
    private Long id;
    private String title;
    private String summary;//简介
//    评论数量
    private int commentCounts;
    private int viewCounts;
    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

//    标签
    private List<TagVo> tags;

    private String author;
//
//    private ArticleBodyVo body;
}
