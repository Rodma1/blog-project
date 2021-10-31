package com.chen.service;

import com.chen.vo.CategoryVo;
import com.chen.vo.Result;

public interface CategoryService {
    //通过文章表的分类id来获取对应的分类
    CategoryVo findCategoryById(Long id);
//    获取所有分类
    Result findAll();
//    查询所有的文章分类
    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
