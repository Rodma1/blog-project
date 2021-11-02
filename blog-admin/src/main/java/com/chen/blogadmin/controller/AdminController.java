package com.chen.blogadmin.controller;


import com.chen.blogadmin.service.PermissionService;
import com.chen.blogadmin.vo.Result;
import com.chen.blogadmin.vo.param.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//返回json格式
@RestController
//设置父路由
@RequestMapping("admin")
public class AdminController {
//    自动注入服务
    @Autowired
    private PermissionService permissionService;
    @GetMapping
    public String test(){
        return "hello";
    }
//    获取管理台permission表中的所有值
    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);

    }
}