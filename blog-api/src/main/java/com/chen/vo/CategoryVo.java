package com.chen.vo;

import lombok.Data;

@Data
public class CategoryVo {

    private Long id;

    private String avatar;
// 标签名
    private String categoryName;
//    描述
    private String description;
}