package com.chen.service;

import com.chen.dao.pojo.SysUser;


public interface SysUserService {
//通过用户id找到用户信息
    SysUser findUserById(Long userId);

//    通过用户密码获取信息
    SysUser findUser(String account,String pwd);
}
