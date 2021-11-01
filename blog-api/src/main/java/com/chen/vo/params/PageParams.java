package com.chen.vo.params;

import lombok.Data;

//设置列表需要的参数
@Data
public class PageParams {
//    当前页数
    private int page=1;
//    页数大小
    private int pageSize=10;
//    分类id
    private Long categoryId;
//  标签id
    private Long tagId;
    /**
     * 获取年月
     */
    private String year;
    private String month;
//    指定以get方法
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            //为什么这里加零，因为会有01,02
            return "0"+this.month;
        }
        return this.month;
    }
}
