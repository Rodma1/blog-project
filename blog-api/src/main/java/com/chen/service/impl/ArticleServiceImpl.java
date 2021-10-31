package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.dao.dos.Archives;
import com.chen.dao.mapper.ArticleBodyMapper;
import com.chen.dao.mapper.ArticleMapper;
import com.chen.dao.mapper.ArticleTagMapper;
import com.chen.dao.pojo.Article;
import com.chen.dao.pojo.ArticleBody;
import com.chen.dao.pojo.ArticleTag;
import com.chen.dao.pojo.SysUser;
import com.chen.service.*;
import com.chen.utils.UserThreadLocal;
import com.chen.vo.*;
import com.chen.vo.params.ArticleParam;
import com.chen.vo.params.PageParams;
import com.sun.jmx.snmp.tasks.ThreadService;
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
//    线程池
    @Autowired
    private TreadService threadService;
//    文章标签表映射
    @Autowired(required = false)
    private ArticleTagMapper articleTagMapper;
//    文章内容表映射
    @Autowired(required = false)
    private ArticleBodyMapper articleBodyMapper;
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
        /**
         * 1. 根据id查询文章信息
         * 2. 根据bodyId和categoryid去做关联查询
         * -- 查询文章内容详情
         * SELECT id,comment_counts,create_date,summary,
         * title,view_counts,weight,author_id,body_id,category_id FROM blog_article WHERE id=#{id}
         */
        Article article = articleMapper.selectById(id);
        //把更新操作扔到线程池中
        threadService.updateViewCount(articleMapper,article);
        return copy(article,true,true,true,true);
    }

    /**
     * 获取文章内容
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
//        获取用户信息,由于我们使用UserThreadLocal获取信息，所以这个文章输入接口要加入到登录拦截器中，因为你登录了才能有用户信息编辑文章
        SysUser sysUser= UserThreadLocal.get();
        /**
         * 所需参数及具体实现
         * 1. 发布文章  目的 构建Article对象
         * 2. 作者id 当前的登录用户
         * 3. 标签  要将标签加入到 并联列表当中
         * 4. body 内容存储 article bodyId
         */
//        将所需数据表所需字段都存入到数据表中
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        Long id=articleParam.getCategory().getId();
        article.setCategoryId(id);
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        //默认为Article_Common
        article.setWeight(Article.Article_Common);
//        在文章最后面id加
        article.setBodyId(-1L);
//        执行插入 ,插入之后 会生成一个文章id
        this.articleMapper.insert(article);
        /**
         * 接下来是标签表的插入
         * 如果标签参数不为空，那么就将文章Id和对应的标签Id存入到文章标签表blog_article_tag
         */
        List<TagVo> tags=articleParam.getTags();
        if (tags!=null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
//                将获取的数据插入到文章标签表
                this.articleTagMapper.insert(articleTag);
            }
        }
        /**
         * 接下来是文章内容表的插入blog_article_body
         */
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBody.setArticleId(article.getId());
//        插入到文章表
        articleBodyMapper.insert(articleBody);
        /**
         * 更新文章表article里面的body_id字段，因为我们文章内容表已经插入了文章内容，文章表里也要产生对应的id跟他匹配
         */
//        获取id
        article.setBodyId(articleBody.getId());
//        执行更新操作
        articleMapper.updateById(article);
        /**
         * 数据已经插入完毕了，接下来就是将文章id返回给前端了，前端就可以通过id在请求文章路由进行文章展示
         */

        ArticleVo articleVo = new ArticleVo();
//        这里直接调用ArticleVo对象，你也可以单独设置一个只返回id的对象
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

}
