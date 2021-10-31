package com.chen.vo.params;

import lombok.Data;

//设置列表需要的参数
@Data
public class PageParams {
//    当前页数
    private int page=1;
//    页数大小
    private int pageSize=10;
//    分类id
    private Long categoryId;
//  标签id
    private Long tagId;
}
