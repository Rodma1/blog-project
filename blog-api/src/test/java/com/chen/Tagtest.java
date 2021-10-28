package com.chen;

import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.mapper.TagMapper;
import com.chen.dao.pojo.Tag;
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
    @Autowired(required = false)
    private TagMapper tagMapper;
    @Test
    void contextLoads() {
        Long i=(long)1;
        System.out.println(11);
        List<Tag> tags=tagMapper.findTagsByArticleId(i);
        System.out.println(tags);
    }
}
