package com.chen;
//文章ArticleMapper测试

import com.chen.dao.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleTest {
    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Test
    void contextLoads() {
        System.out.println(articleMapper.listArchives());
    }
}
