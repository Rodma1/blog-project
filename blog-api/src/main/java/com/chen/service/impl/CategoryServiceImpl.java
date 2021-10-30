package com.chen.service.impl;


import com.chen.dao.mapper.CategoryMapper;
import com.chen.dao.pojo.Category;
import com.chen.service.CategoryService;
import com.chen.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired(required = false)
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long id) {
        Category category=categoryMapper.selectById(id);
//        新建对象
        CategoryVo categoryVo = new CategoryVo();
//        获取categoryVo需要的属性
        BeanUtils.copyProperties(category,categoryVo);
//        返回
        return categoryVo;
    }
}
