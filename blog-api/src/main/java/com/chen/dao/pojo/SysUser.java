package com.chen.dao.pojo;
//用户
import lombok.Data;

@Data
public class SysUser {
    private Long id;
//    创建时间
    private Long create_date;
//    账号
    private String account;
//    头像
    private String avatar;
//邮箱
    private String email;
//电话
    private String mobile_phone_number;
//昵称
    private String nickname;
//密码
    private String password;
//加密
    private String salt;
//状态
    private String status;
//    是否为管理员
    private Integer admin;
//  是否删除
    private Integer deleted;
}
