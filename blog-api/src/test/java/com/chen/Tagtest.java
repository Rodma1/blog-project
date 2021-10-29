package com.chen;

import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.mapper.TagMapper;
import com.chen.dao.pojo.Tag;
import com.chen.service.TagService;
import com.chen.service.impl.TagsServiceImpl;
import com.chen.vo.TagVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@SpringBootTest
public class Tagtest {
    @Autowired
    private TagService tagService;
    @Autowired(required = false)
    private TagMapper tagMapper;
    @Test
    void contextLoads() {
        Long i=(long)1;
        System.out.println(11);
        List<TagVo> tags=tagService.findTagsByArticleId(i);
        System.out.println("文章"+i+"的标签是"+tags);
        List<Long> list=tagMapper.findHotsTagIds(2);
        System.out.println("前两个最热标签"+list);

        List<Tag> tagname=tagMapper.findTagsByTagIds(list);
        System.out.println("最热标签的名字"+tagname);

//        返回给前端的数据
        List<TagVo> tagVoList=tagService.hot(2);
        System.out.println(tagVoList);


    }
}
