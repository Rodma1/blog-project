package com.chen.vo;

import lombok.Data;

import java.util.List;

/**
 * 返回给前端的评论数据
 */
@Data
public class CommentVo {
    private Long id;
//    评论内容
    private String content;
//    子评论
    private List<CommentVo> childrens;
//    创建时间
    private String createDate;
//    层数
    private Integer level;
//    用户信息
    private UserVo toUser;
//
    private UserVo author;

}
