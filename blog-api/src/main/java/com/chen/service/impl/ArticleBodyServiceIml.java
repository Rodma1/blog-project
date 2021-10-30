package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.ArticleBodyMapper;
import com.chen.dao.pojo.ArticleBody;
import com.chen.service.ArticleBodyService;
import com.chen.vo.ArticleBodyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleBodyServiceIml implements ArticleBodyService {
    @Autowired(required = false)
    private ArticleBodyMapper articleBodyMapper;
    @Override
    public ArticleBodyVo findArticleBody(long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId,articleId);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
//        返回对应的文章内容
        return articleBodyVo;
    }
}
