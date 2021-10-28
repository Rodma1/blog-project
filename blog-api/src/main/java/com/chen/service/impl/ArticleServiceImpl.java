package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.pojo.Article;
import com.chen.dao.pojo.SysUser;
import com.chen.service.ArticleService;
import com.chen.service.SysUserService;
import com.chen.service.TagService;
import com.chen.vo.ArticleVo;
import com.chen.vo.Result;
import com.chen.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//实现文章服务层的接口
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;

    @Override
    public Result listArticlesPage(PageParams pageParams) {
        /*
        分页查询数据库
        baomidou已经自带了分页接口
         */
//        创建页数对象
        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        查询条件
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        是否进行排序
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage=articleMapper.selectPage(page,queryWrapper);
        List<Article> records=articlePage.getRecords();
//        默认返回tag标签true
        List<ArticleVo> articleVoList=copyList(records,true,true);
        return Result.success(articleVoList);
    }
    //        循环遍历到列表中
    private  List<ArticleVo> copyList(List<Article> records,boolean isTags,boolean isAuthor){
        List<ArticleVo> articleVoList=new ArrayList<>();

        for (Article record:records){
            articleVoList.add(copy(record,isTags,isAuthor));

        }
        return  articleVoList;
    }
    private ArticleVo copy(Article article,boolean isTags,boolean isAuthor){
//        new对象
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有接口都需要用户和标签
        if (isTags){//如果需要显示标签
//            List<TagVo> tags=tagsService.findTagsByArticleId
            Long articleId=article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));

        }
        //并不是所有接口都需要用户和标签
        if (isAuthor) {
            SysUser sysUser = sysUserService.findUserById(article.getAuthorId());
            articleVo.setAuthor(sysUser.getNickname());
        }

        return articleVo;
    }
}
