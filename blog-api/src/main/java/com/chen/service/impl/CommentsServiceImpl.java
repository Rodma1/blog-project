package com.chen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chen.dao.mapper.CommentMapper;
import com.chen.dao.pojo.Comment;
import com.chen.dao.pojo.SysUser;
import com.chen.service.CommentsService;
import com.chen.service.SysUserService;
import com.chen.utils.UserThreadLocal;
import com.chen.vo.CommentVo;
import com.chen.vo.Result;
import com.chen.vo.UserVo;
import com.chen.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现评论列表的获取
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
    public Result commentsByArticleId(Long articleId) {
//        定义mapper查询评论表里面的信息
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
//        查找对应的文章和层级为1的评论
        queryWrapper.eq(Comment:: getArticleId,articleId);
        queryWrapper.eq(Comment:: getLevel,1);
//        执行语句：select * from blog_comment where article_id=1 and level=1;
        List<Comment> comments=commentMapper.selectList(queryWrapper);
        return Result.success(copyList(comments));
    }
    /**
     * 遍历追加
     * @param commentList
     * @return
     */
    public List<CommentVo> copyList(List<Comment> commentList){
//        定义一个动态数组
        List<CommentVo> commentVoList=new ArrayList<>();
//        遍历每一个查询到的评论信息
        for (Comment comment :commentList){
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }
    /**
     * 追加用户元素
     */
    public CommentVo copy(Comment comment){
//        创建一个对象，这个用于返回给前端的数据
        CommentVo commentVo=new CommentVo();
//        转化为String类型
        commentVo.setId(String.valueOf(comment.getId()));
//        把comment中与commentVo有相同属性的字段copy进去
        BeanUtils.copyProperties(comment,commentVo);
//        时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
//        获取作者信息
        Long authorId=comment.getAuthorId();//获取id
        UserVo userVo=sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
//        找子评论
        Integer level=comment.getLevel();
        if (1==level){
            Long id=comment.getId();
            //        获取评论的评论
            List<CommentVo> commentVoList=findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
//        如果大于一说明这个评论是子评论
        if (level>1){
//            找到所评论的目标人id
            Long toUid=comment.getToUid();
//            获取这个id的用户信息
            UserVo toUserVo=sysUserService.findUserVoById(toUid);
//            放入到commentVo中
            commentVo.setToUser(toUserVo);
        }
        return  commentVo;
    }
//    评论的评论
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
//        查询父评论的id和水平是否为2
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }
    /**
     * 评论功能的实现
     */
    @Override
    public Result comment(CommentParam commentParam) {
        //之前我们已经写好了，用户登录完我们会把用户信息存入到UserThreadLocal里面
        SysUser sysUser= UserThreadLocal.get();
//        创建新的评论对象,获取属性
        Comment comment=new Comment();
//        文章id
        comment.setArticleId(commentParam.getArticleId());
//        作者id
        comment.setAuthorId(sysUser.getId());
//        评论内容
        comment.setContent(commentParam.getContent());
//        评论时间
        comment.setCreateDate(System.currentTimeMillis());
        /**
         * 判断是否是父评论，获取level等级
         */
        Long parent=commentParam.getParent();
//        如果parent没有返回id，也就是默认为0，说明他是父评论
        if (parent==null||parent==0){
            comment.setLevel(1);
        }else{
//            否则就是子评论
            comment.setLevel(2);
        }
//        给这个新的评论添加parent参数
        comment.setParentId(parent == null ? 0 : parent);
//        在获取评论的用户id
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
//        执行插入语句
        this.commentMapper.insert(comment);
        //写评论是直接写入数据库，不需要返回数据,所以这里为null
        return Result.success(null);
    }
}
