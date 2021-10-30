package com.chen.utils;
//这里是存储用户信息，每个用户都可以通过这里开辟一个UserThreadLocal对象线程put存放信息，用完了记得remove
import com.chen.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}
// 线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();
// 用户信息放入
    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    //取
    public static SysUser get(){
        return LOCAL.get();
    }
    //删除
    public static void remove(){
        LOCAL.remove();
    }
}