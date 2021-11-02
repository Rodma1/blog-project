package com.chen.blogadmin.service;

import com.chen.blogadmin.vo.Result;
import com.chen.blogadmin.vo.param.PageParam;

public interface PermissionService {
//    获取控制台数据
    Result listPermission(PageParam pageParam);
}
