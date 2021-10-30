package com.chen.dao.pojo;
import com.chen.vo.ArticleBodyVo;
import com.chen.vo.CategoryVo;
import com.chen.vo.TagVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

//创建文章实体类
@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;
//    评论数量
    private int commentCounts;
//    创建时间
    private Long createDate;
//    简介
    private String summary;
//    标题
    private String title;
//    流量数据
    private int viewCounts;
//    是否置顶
    private int weight = Article_Common;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;


}
