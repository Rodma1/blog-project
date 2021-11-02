package com.chen.blogadmin.vo.param;
/**
 * 前端传递过来的参数
 */

import lombok.Data;

@Data
public class PageParam {
//当前页数
    private Integer currentPage;
//想要多少数据在这页
    private Integer pageSize;
//查询条件
    private String queryString;
}