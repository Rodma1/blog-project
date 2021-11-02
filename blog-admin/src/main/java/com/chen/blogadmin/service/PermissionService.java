package com.chen.blogadmin.service;

import com.chen.blogadmin.dao.pojo.Permission;
import com.chen.blogadmin.vo.Result;
import com.chen.blogadmin.vo.param.PageParam;

public interface PermissionService {
//    获取控制台数据
    Result listPermission(PageParam pageParam);
//    添加数据
    Result add(Permission permission);
//    更新数据
    Result update(Permission permission);
//    删除数据
    Result delete(Long id);
}
