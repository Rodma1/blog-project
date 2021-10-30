package com.chen.vo;
//设置返回给前端的参数
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
//    设置返回给前端需要的参数
    @JsonSerialize(using = ToStringSerializer.class)//使用fastjson的ToStringSerializer注解，让系统序列化时，保留相关精度
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
    //    文章内容
    private ArticleBodyVo body;
    //  文章类别
    private CategoryVo category;
}
