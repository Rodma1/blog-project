package com.chen.service;

import com.chen.dao.pojo.SysUser;
import com.chen.vo.Result;


public interface SysUserService {
//通过用户id找到用户信息
    SysUser findUserById(Long userId);

//    通过用户密码获取信息
    SysUser findUser(String account,String pwd);
//    通过token获取用户信息
    Result getUserInfoByToken(String token);

//    查找用户名是否存在
    SysUser findUserByAccount(String account);
//    保存注册的用户信息
    void save(SysUser sysUser);

}
