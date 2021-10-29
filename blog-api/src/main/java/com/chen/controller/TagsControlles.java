package com.chen.controller;

import com.chen.service.TagService;
import com.chen.vo.Result;
import com.chen.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//请求json数据
@RestController
//主路由
@RequestMapping("tags")
public class TagsControlles {
    @Autowired
    private TagService tagService;
//    设置子路由get请求
    @GetMapping("/hot")
    public Result listHotTags(){
        int limit =3;
//        获取对应的标签名
        List<TagVo> tagVoList=tagService.hot(limit);
//        返回json数据
        return Result.success(tagVoList);
    }
}
