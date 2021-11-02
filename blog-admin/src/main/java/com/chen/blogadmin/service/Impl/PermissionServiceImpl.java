package com.chen.blogadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.blogadmin.dao.mapper.PermissionMapper;
import com.chen.blogadmin.dao.pojo.Permission;
import com.chen.blogadmin.service.PermissionService;
import com.chen.blogadmin.vo.PageResultVo;
import com.chen.blogadmin.vo.Result;
import com.chen.blogadmin.vo.param.PageParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired(required = false)
    private PermissionMapper permissionMapper;
    @Override
    public Result listPermission(PageParam pageParam){
//        获取页数
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
//        初始化数据库映射Mapper
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
//        查询条件StringUtils.isNotBlank可以判断null和空格
        if (StringUtils.isNotBlank(pageParam.getQueryString())) {
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
//        执行查询语句，查询控制台permission表中的数据select * from blog_permission where name=queryWrapper limit page
        Page<Permission> permissionPage = this.permissionMapper.selectPage(page, queryWrapper);
//        将数据送到前端
        PageResultVo<Permission> pageResult = new PageResultVo<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }
}
