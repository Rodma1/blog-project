package com.chen.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    //密钥，只要这个密钥不暴露给别人，那么jwt字符串就是安全的
    private static final String jwtToken = "12345abMszlu!@#$$";

    public static String createToken(Long userId){
//        设置哈希值
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)//A 签发算法，秘钥为jwtToken
                .setClaims(claims) //B body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            //解析
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
//    测试
    public static void main(String[] args){
//        创建token
        String token=JWTUtils.createToken(100L);
        System.out.println(token);
//        验证token
        Map<String ,Object > map=JWTUtils.checkToken(token);
        System.out.println(map);
//        获取用户id
        System.out.println(map.get("userId"));

    }

}
