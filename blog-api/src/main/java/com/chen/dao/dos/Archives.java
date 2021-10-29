package com.chen.dao.dos;
//文章归档所要获取的时间和文章数量
import lombok.Data;

@Data
public class Archives {
    private Integer year;
    private Integer month;
    private Integer count;
}
