package com.chen;

import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.pojo.Article;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlogApiApplicationTests {
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Test
    void contextLoads() {
        List<Article> list=articleMapper.selectList(null);
        System.out.println("list"+list+"size"+list.size());
    }

}
