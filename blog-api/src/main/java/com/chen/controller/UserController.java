package com.chen.controller;

import com.chen.service.SysUserService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization")  String token){
        System.out.println(token);
        return sysUserService.getUserInfoByToken(token);
    }
}
