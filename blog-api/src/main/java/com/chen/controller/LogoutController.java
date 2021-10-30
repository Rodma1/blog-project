package com.chen.controller;


import com.chen.service.LoginService;
import com.chen.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//返回json数据
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;
//    get请求
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
//        运行
        return loginService.logout(token);
    }
}
