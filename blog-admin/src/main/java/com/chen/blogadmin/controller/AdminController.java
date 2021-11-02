package com.chen.blogadmin.controller;


import com.chen.blogadmin.dao.pojo.Permission;
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

    /**
     * 添加
     * @param permission
     * @return
     */
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){

        return permissionService.add(permission);
    }

    /**
     * 更新
     * @param permission
     * @return
     */
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}