package com.chen.vo;

import lombok.Data;

/**
 * 用于返回给评论信息用的
 */
@Data
public class UserVo {
    private Long id;
//    用户昵称
    private String nickname;
//    用户头像
    private String avatar;

}
