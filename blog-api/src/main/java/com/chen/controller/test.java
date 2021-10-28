package com.chen.controller;

import com.chen.dao.mapper.TagMapper;
import com.chen.dao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {
    @Autowired(required = false)
    TagMapper tagMapper;
    // 查询全部部门
    @GetMapping("/tagMapper/{id}")
    public List<Tag> getDepartment(@PathVariable("id") int id){

        return tagMapper.findTagsByArticleId((long)id);
    }

}
