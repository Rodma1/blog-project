package com.chen.controller;

import com.chen.dao.pojo.SysUser;
import com.chen.utils.UserThreadLocal;
import com.chen.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        //        SysUser
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(sysUser);
    }
}
