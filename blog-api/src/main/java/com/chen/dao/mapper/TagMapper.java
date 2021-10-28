package com.chen.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /*
    根据文章id查询标签列表
     */
    List<Tag> findTagsByArticleId(Long articleId);
}
