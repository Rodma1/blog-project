package com.chen.service.impl;

import com.chen.dao.mapper.TagMapper;
import com.chen.dao.pojo.Tag;
import com.chen.service.TagService;
import com.chen.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagService {
    @Autowired(required = false)
    private TagMapper tagMapper;
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
//        tag拷贝到tagVo
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

//    这个是要返回给前端的数据
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList=new  ArrayList<>();
        for (Tag tag:tagList){
            tagVoList.add(copy(tag));
        }
        return  tagVoList;

    }
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
//        根据文章id获取数据库对应的标签信息
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }
}
