package com.chen.vo.params;
//用户密码登录参数
import lombok.Data;

@Data
public class LoginParam {
//    账号
    private String account;
//    密码
    private String password;
//    昵称
    private String nickname;
}
