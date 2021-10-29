package com.chen.vo.params;
//用户密码登录参数
import lombok.Data;

@Data
public class LoginParam {
    private String account;
    private String password;
}
