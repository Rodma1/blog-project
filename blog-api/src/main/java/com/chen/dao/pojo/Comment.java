package com.chen.dao.pojo;

import lombok.Data;
/**
 *评论的数据表字段
 */

@Data
public class Comment {
    private Long id;
//    评论内容
    private String content;
//    创建时间
    private Long createDate;
//    文章id
    private Long articleId;
//  评论人的id
    private Long authorId;
//   所评论的那个评论的id
    private Long parentId;
//    评论目标人的id
    private Long toUid;
//  评论的水平，就是有几层
    private Integer level;
}
