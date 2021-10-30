package com.chen.service;

import com.chen.dao.pojo.SysUser;
import com.chen.vo.Result;


public interface SysUserService {
//通过用户id找到用户信息
    SysUser findUserById(Long userId);

//    通过用户密码获取信息
    SysUser findUser(String account,String pwd);
//    通过token获取信息
    Result getUserInfoByToken(String token);
}
