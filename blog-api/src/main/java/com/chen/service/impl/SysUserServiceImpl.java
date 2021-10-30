package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.SysUserMapper;
import com.chen.dao.pojo.SysUser;
import com.chen.service.SysUserService;
import com.chen.utils.JWTUtils;
import com.chen.vo.ErrorCode;
import com.chen.vo.LoginUserVo;
import com.chen.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    @Autowired
    //    springboot和redis整合
    private RedisTemplate<String,String> redisTemplate;

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
// 通过redis的token获取用户信息
    @Override
    public Result getUserInfoByToken(String token) {
//        解析token
        Map<String,Object> map= JWTUtils.checkToken(token);
//        如果参数为null，token过期，未登录，就返回错误
        if (map == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
//        获取redis里面的token
        String userJson =  redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
//      解析userJson获取SysUser类型的属性
        SysUser sysUser= JSON.parseObject(userJson,SysUser.class);
//        创建LoginUseVo对象，用户返回前端的数据
        LoginUserVo loginUserVo=new LoginUserVo();
//        用户名
        loginUserVo.setAccount(sysUser.getAccount());
//        头像
        loginUserVo.setAvatar(sysUser.getAvatar());
//        用户id
        loginUserVo.setId(sysUser.getId());
//        昵称
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);

    }


}
