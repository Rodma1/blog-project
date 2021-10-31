package com.chen.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 需要前端传入的评论参数
 */
@Data
public class CommentParam {
//文章id
//防止前端 精度损失 把id转为string
// 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;
//评论内容
    private String content;
//评论的等级
    private Long parent;
//给那个用户id评论
    private Long toUserId;
}
