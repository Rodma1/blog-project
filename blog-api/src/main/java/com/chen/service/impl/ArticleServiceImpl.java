package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.dao.dos.Archives;
import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.pojo.Article;
import com.chen.dao.pojo.SysUser;
import com.chen.service.*;
import com.chen.vo.ArticleBodyVo;
import com.chen.vo.ArticleVo;
import com.chen.vo.CategoryVo;
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
//文章类别
    @Autowired
    private CategoryService categoryService;
//    文章内容
    @Autowired
    private ArticleBodyService articleBodyService;
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
    //        循环遍历到列表中，这里的copyList使用到了重载
    private  List<ArticleVo> copyList(List<Article> records,boolean isTags,boolean isAuthor){
        List<ArticleVo> articleVoList=new ArrayList<>();

        for (Article record:records){
            articleVoList.add(copy(record,isTags,isAuthor,false,false));

        }
        return  articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }
    private ArticleVo copy(Article article,boolean isTags,boolean isAuthor, boolean isBody, boolean isCategory){
//        new对象
        ArticleVo articleVo=new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口 都需要标签 ，作者信息
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
//        内容
        if (isBody){
            ArticleBodyVo articleBody = articleBodyService.findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }
//        类别
        if (isCategory){
            CategoryVo categoryVo = categoryService.findCategoryById(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }
//    最热文章查询语句实现,返回他的id和标题
    @Override
    public  Result hotArticle(int limit){
//        初始化mapper映射,查询Article数据表的信息
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        设置排序，ViewCounts浏览量倒序排
        queryWrapper.orderByDesc(Article::getViewCounts);
//        只查询id和title
        queryWrapper.select(Article::getId,Article::getTitle);
//        最后在加上前limit几个,注意这里的空格
        queryWrapper.last("limit "+limit);
//        执行查询语句，相当于select id,title from article order by view_counts desc limit #{limit}
        List<Article> articles =articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    //    最新文章查询语句实现,返回他的id和标题,通过比较创建时间
    @Override
    public  Result newArticles(int limit){
//        初始化mapper映射,查询Article数据表的信息
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
//        设置排序，CreateDate创建时间倒序排
        queryWrapper.orderByDesc(Article::getCreateDate);
//        只查询id和title
        queryWrapper.select(Article::getId,Article::getTitle);
//        最后在加上前limit几个,注意这里的空格
        queryWrapper.last("limit "+limit);
//        执行查询语句，相当于select id,title from article order by create_data desc limit #{limit}
        List<Article> articles =articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }
    //    文章归档
    @Override
    public Result listArchives() {
//        获取mapper映射查询到的数据
        List<Archives> archivesList= articleMapper.listArchives();
        return Result.success(archivesList);
    }
    //   点击进入文章内容查看详情
    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);

        return copy(article,true,true,true,true);
    }


}
