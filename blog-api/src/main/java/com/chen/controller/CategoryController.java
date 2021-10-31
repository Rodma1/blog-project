package com.chen.controller;

import com.chen.service.CategoryService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * get请求就可以了，获取CategoryVo要拿的分类
     */
    @GetMapping
    public Result listCategory(){
        return categoryService.findAll();
    }
    /**
     * get请求，获取所有分类
     */
    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
    /**
     * 通过类别分类文章列表
     */
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
//        通过类别id查询对应的文章显示
        return categoryService.categoriesDetailById(id);
    }


}
