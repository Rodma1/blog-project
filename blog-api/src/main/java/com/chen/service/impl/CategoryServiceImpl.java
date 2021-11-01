package com.chen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.CategoryMapper;
import com.chen.dao.pojo.Category;
import com.chen.service.CategoryService;
import com.chen.vo.CategoryVo;
import com.chen.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
//        转为String类型
        categoryVo.setId(String.valueOf(category.getId()));
//        返回
        return categoryVo;
    }

    /**
     * 获取分类表的所有分类
     * @return Result
     */
    @Override
    public Result findAll() {
//        相当于sql:select * from blog_category;
        List<Category> categories=this.categoryMapper.selectList(new LambdaQueryWrapper<>());
//        返回页面交互对象
        return Result.success(copyList(categories));
    }
    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setId(String.valueOf(category.getId()));
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }

    /**
     * 查询所有的文章分类
     * 接口url：/categorys/detail
     * @return
     */
    @Override
    public Result findAllDetail() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        //页面交互的对象
        return Result.success(copyList(categories));
    }

    /**
     * 通过类别id对文章列表进行分类
     * @param id
     * @return
     */
    @Override
    public Result categoriesDetailById(Long id) {
//        查询对应的类别id
        Category category=categoryMapper.selectById(id);
//        对应CategoryVo里面的值返回
        CategoryVo categoryVo=copy(category);
        return Result.success(categoryVo);

    }
}
