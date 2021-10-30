package com.chen.service.impl;
import com.alibaba.fastjson.JSON;
import com.chen.dao.pojo.SysUser;
import com.chen.service.LoginService;
import com.chen.service.SysUserService;
import com.chen.utils.JWTUtils;
import com.chen.vo.ErrorCode;
import com.chen.vo.Result;
import com.chen.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//实现登录接口
@Service
public class LoginServiceImpl implements LoginService {
    private static final String slat ="adfg!@#";
    @Autowired
//    自动注入用户服务
    private SysUserService sysUserService;
//    springboot和redis整合
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Result login(LoginParam loginParam) {
//        获取用户
        String account=loginParam.getAccount();
//        获取密码
        String password=loginParam.getPassword();
//        检查参数是否合法,如果校验失败就返回状态码,StringUtils判断字符串是否为空
          if (StringUtils.isBlank(account)||StringUtils.isBlank(password)){
              return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
          }
//         slat加密盐给死了，也可以自己在数据库设计
        String pwd= DigestUtils.md5Hex(account+slat);
//          获取用户信息
        SysUser sysUser=sysUserService.findUser(account,pwd);
//        根据用户名和密码去user表中查询是否存在，如果不存在登陆失败,
        if (sysUser==null){//如果用户信息为空为空
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
//登录成功，使用jwt生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        //存入redis
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
//        token放入redis当中，redis token:user信息 设置过期时间（登录认证的时候 先认证token字符串是否合法，去redis认证是否存在）
    }
    //    public static void main(String[] args) {
//        System.out.println(DigestUtils.md5Hex("admin"+slat));
//    }

//    退出登录
    @Override
    public Result logout(String token) {
//        删除缓存中的token
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

}
