package com.chen.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /*
    根据文章id查询标签列表
     */
    List<Tag> findTagsByArticleId(Long articleId);
//    找到文章中最热火的前几个标签
    List<Long> findHotsTagIds(int limit);
//    通过id（List<Long>）找到标签对应的名字
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
