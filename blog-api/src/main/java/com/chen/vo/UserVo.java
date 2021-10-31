package com.chen.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 用于返回给评论信息用的
 */
@Data
public class UserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
//    用户昵称
    private String nickname;
//    用户头像
    private String avatar;

}
