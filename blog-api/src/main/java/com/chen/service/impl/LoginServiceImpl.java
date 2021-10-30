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
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.NestingKind;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//实现登录接口
@Service
@Transactional
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
    /*
    注册
     */
    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1. 判断参数 是否合法
         * 2. 判断账户是否存在 如果存在 返回账户已经被注册，不存在就注册
         * 3. 生成token
         * 4. 存入redis 并返回
         * 6. 注意加上事物，一旦中间的任何过程出现问题，注册的用户需要回滚
         */
//        获取指定参数
        String account=loginParam.getAccount();
        String password=loginParam.getPassword();
        String nickname=loginParam.getNickname();
//        判断是否合法
        if (StringUtils.isBlank(account)||StringUtils.isBlank(password)
                ||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
//        判断用户名是否存在
        SysUser sysUser=this.sysUserService.findUserByAccount(account);
//        如果存在就返回错误
        if (sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
//        如果不存在用户就将这个新用户存进去
        sysUser=new SysUser();
//        获取用户数据表里的数据
        sysUser.setAccount(account);
        sysUser.setNickname(nickname);
//      密码加密也要+上slat，因为我们上面登录判断的时候也是这样子，要保持一致
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreate_date(System.currentTimeMillis());
        sysUser.setLast_login(System.currentTimeMillis());
//       初始化头像
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
//        保存用户到数据库
        this.sysUserService.save(sysUser);
//        生成token
        String token=JWTUtils.createToken(sysUser.getId());
//        token和用户信息存在redis,并且只能保存一天
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
//        返回tonken到请求头
        return Result.success(token);
    }
    //    验证token
    @Override
    public SysUser checkToken(String token){
        if (StringUtils.isBlank(token)){
            return null;
        }
//        如果验证失败
        Map<String,Object> stringObjectMap=JWTUtils.checkToken(token);
        if (stringObjectMap==null){
            return null;
        }
//        获取token
        String userJson=redisTemplate.opsForValue().get("TOKEN_"+token);
        if (StringUtils.isBlank(userJson)){
            return  null;
        }
        SysUser sysUser=JSON.parseObject(userJson,SysUser.class);
        return sysUser;

    }


}
