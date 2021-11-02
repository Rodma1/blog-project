package com.chen.blogadmin.vo;
/**
 * 每一页的数据
 */

import lombok.Data;

import java.util.List;

@Data
public class PageResultVo<T> {
//permission列表数据
    private List<T> list;
//返回总数
    private Long total;
}