package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.SysUserMapper;
import com.chen.dao.pojo.SysUser;
import com.chen.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;


    @Override
    public SysUser findUserById(Long userid) {
        SysUser sysUser=sysUserMapper.selectById(userid);
//        如果没有用户名就默认
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("运智");
        }
        return sysUser;
    }
    //    通过用户名和密码配对用户信息返回
    @Override
    public SysUser findUser(String account, String pwd) {
//        设置mapper映射
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
//        比较配对
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,pwd);
//        要查询的的字段
        queryWrapper.select(SysUser::getId,SysUser::getAccount,
                SysUser::getAvatar,SysUser::getNickname);
//        只查询一行
        queryWrapper.last("limit 1");
//        执行select语句
        SysUser sysUser=sysUserMapper.selectOne(queryWrapper);
//        返回查询到的语句
        return sysUser;
    }


}
