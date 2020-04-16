package com.xlx.majiang.system.service;

import com.xlx.majiang.common.enums.CommentTypeEnum;
import com.xlx.majiang.common.enums.NotificationStatusEnum;
import com.xlx.majiang.common.enums.NotificationTypeEnum;
import com.xlx.majiang.system.dao.*;
import com.xlx.majiang.system.dto.CommentDTO;
import com.xlx.majiang.system.entity.*;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service层-Comment
 *
 * @author xielx on 2019/6/23
 */
@Service
public class CommentService {
    
    
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionExtraMapper questionExtraMapper;
    @Resource
    private CommentExtraMapper commentExtraMapper;
    
    @Resource
    private NotificationMapper notificationMapper;
    
    @Resource
    private UserMapper userMapper;
    
    /**
     * 回复创建 :
     * 前提:
     * 1.存在该问题的id
     * 2.回复类型存在
     * 可能是回复别人的问题:
     * 1.类型为枚举 'QUESTION(1)'
     * ----才能回复
     * 可能是回复别人的评论:
     * 1.类型为枚举 'COMMENT(2)'
     * 2.对你的评论要存在的,
     * -----才能回复
     *
     * @param comment 评论对象
     * @param user    评论者
     */
    @Transactional
    public void insert(Comment comment, User user) {
        //该评论的问题不存在情况
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(ErrorCodeEnum.TARGET_PARAM_NOT_FOUND);
        }
        
        // 评论/回复类型不存在情况
        if (comment.getType() == null || !CommentTypeEnum.isExists(comment.getType())) {
            throw new CustomizeException(ErrorCodeEnum.TYPE_PARAM_WRONG);
        }
        
        /*===================是回复评论的'COMMENT(2)'类型=========================*/
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //获取
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(ErrorCodeEnum.COMMENT_NOT_FOUND);
            }
            
            //判断关于该评论的问题存在?
            Question dbQuestion = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(ErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            
            // 都存在,回复评论
            commentMapper.insert(comment);
            
            //评论了问题的回答,该回答的评论数需要+1
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            //
            commentExtraMapper.incCommentCount(parentComment);
            
            
            //新增通知,有人回复了评论
            createNotify(comment, dbComment.getCommentator(), user.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_COMMRNTS, dbQuestion.getId());
        } else {
            /*=====================是回答问题'QUESTION(1)'类型================*/
            
            //获取问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(ErrorCodeEnum.QUESTION_NOT_FOUND);
            }
            
            // 插入回答
            commentMapper.insert(comment);
            
            //修改获取问题的评论量+1
            dbQuestion.setCommentCount(1);
            //
            questionExtraMapper.incCommentCount(dbQuestion);
            
            //新增通知,有人回答了问题
            createNotify(comment, dbQuestion.getCreator(), user.getName(), dbQuestion.getTitle(), NotificationTypeEnum.REPLY_QUESTION, dbQuestion.getId());
            
        }
        
    }
    
    /**
     * 依据问题的id与评论的type获取所有的comment(要封装一下)
     * 思路:
     * A:获取评论类型为Question/Comment的CommentDTO
     * 1.查询出parentId=指定问题id和type=Question/Comment的comment结果集
     * 2.从结果集里筛选出不重复的commentator(评论人,userId)
     * 3.根据commentator获取User对象集(Map<userId,User>存储)
     * 4.CommentDTO = comment对象 + 对应的User对象
     *
     * @param parentId        问题id
     * @param commentTypeEnum Comment类型
     * @return list
     */
    public List<CommentDTO> listCommentByIdType(Long parentId, CommentTypeEnum commentTypeEnum) {
        // 查询条件
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(parentId).andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        
        // 根据parentId和type获取所有Comment评论对象集合
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        
        if (commentList.size() == 0) {
            return new ArrayList<>();
        }
        
        //获取Comment对象集里的评论人commentator,不要重复(存在多次评论,就是去重的userId)
        Set<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIdList = new LinkedList<>();
        userIdList.addAll(commentators);
        
        
        // 根据去重的评论人commentator(根据userId)获取对应的User对象,Map存储,Map<userId,user>
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIdList);
        
        List<User> userList = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        
        // 封装评论对象和对象的用户
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        
        return commentDTOList;
        
    }
    
    public String getParentComment(Long parentId) {
        //二级菜单显示时,加入父级的评论,如B评论A: '很好啊' //@A "离职后会怎样"
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(parentId);
        return "";
    }
    
    
    /**
     * 通知的新增
     *
     * @param comment          回复对象
     * @param receiver         接收通知人(告知你发的问题有人回答)
     * @param notifierName     发送通知人(回答的人)
     * @param outerTitle       问题的标题
     * @param notificationType 通知类型(回答了问题/回复了评论)
     * @param outerId          问题id
     */
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        
        Notification notification = new Notification();
        
        //创建时间
        notification.setGmtCreate(System.currentTimeMillis());
        //设置通知类型,回复了评论/回复了问题
        notification.setType(notificationType.getType());
        //问题的id
        notification.setOuterId(outerId);
        //评论创建者id
        notification.setNotifier(comment.getCommentator());
        //通知状态首次创建都是未读0
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        //通知接收者(被评论方)
        notification.setReceiver(receiver);
        //通知发送者(评论方)
        notification.setNotifierName(notifierName);
        //问题的标题
        notification.setOuterTitle(outerTitle);
        
        notificationMapper.insert(notification);
    }
    
    
}
