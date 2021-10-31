package com.chen.vo.params;
import com.chen.vo.CategoryVo;
import com.chen.vo.TagVo;
import lombok.Data;
import java.util.List;
/**
 * 文章参数
 */
@Data
public class ArticleParam {
    private Long id;
//    文章内容
    private ArticleBodyParam body;
//    文章类别
    private CategoryVo category;
//  文章概述
    private String summary;
//    文章标签
    private List<TagVo> tags;
//    标题
    private String title;

}
