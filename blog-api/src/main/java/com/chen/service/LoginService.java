package com.chen.service;

import com.chen.vo.Result;
import com.chen.vo.params.LoginParam;

public interface LoginService {
//    登录接口，通过传入的参数判断
    Result login(LoginParam loginParam);
//    退出登录
    Result logout(String token);
//    注册
    Result register(LoginParam loginParam);
}
