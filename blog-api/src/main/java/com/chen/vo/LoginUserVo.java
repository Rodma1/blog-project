package com.chen.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

//返回到前端的用户信息
@Data
public class LoginUserVo {
//    用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
//    用户名
    private String account;
//    昵称
    private String nickname;
//    用户头像
    private String avatar;
}
