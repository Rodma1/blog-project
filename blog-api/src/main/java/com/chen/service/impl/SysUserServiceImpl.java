package com.chen.service.impl;

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
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("运智");
        }
        return sysUser;
    }
}
