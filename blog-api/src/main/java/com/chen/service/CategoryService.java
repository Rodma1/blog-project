package com.chen.service;

import com.chen.vo.CategoryVo;

public interface CategoryService {
    //通过文章表的标签id来获取对应的标签
    CategoryVo findCategoryById(Long id);
}
