package com.xlx.majiang.service;

import com.xlx.majiang.dto.CommentDTO;
import com.xlx.majiang.enums.CommentTypeEnum;
import com.xlx.majiang.enums.NotificationStatusEnum;
import com.xlx.majiang.enums.NotificationTypeEnum;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import com.xlx.majiang.mapper.*;
import com.xlx.majiang.model.*;
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
  private QuestionExtMapper questionExtMapper;
  @Resource
  private CommentExtMapper commentExtMapper;

  @Resource
  private NotificationMapper notificationMapper;

  @Resource
  private UserMapper userMapper;

  /**
   * 回复创建 :
   *   前提:
   *   1.存在该问题的id
   *   2.回复类型存在
   *  可能是回复别人的问题:
   *     1.类型为枚举 'QUESTION(1)'
   *    ----才能回复
   * 可能是回复别人的评论:
   *    1.类型为枚举 'COMMENT(2)'
   *    2.对你的评论要存在的,
   *   -----才能回复
   *
   * @param comment 评论对象
   * @param user 评论者
   */
  @Transactional
  public void insert (Comment comment, User user){
    //处理该评论的问题不存在情况
    if (comment.getParentId() == null || comment.getParentId() == 0){
      throw  new CustomizeException(CustomizeErrorCodeEnum.TARGET_PARAM_NOT_FOUND);
    }

    // 处理评论/回复类型不存在情况
    if (comment.getType() == null || !CommentTypeEnum.isExists(comment.getType())){
      throw  new CustomizeException(CustomizeErrorCodeEnum.TYPE_PARAM_WRONG);
    }

    /*===================是'COMMENT(2)'类型=========================*/
    if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
      //判断别人对你的评论存在?
      Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
      if (dbComment == null){
        throw new CustomizeException(CustomizeErrorCodeEnum.COMMENT_NOT_FOUND);
      }

      //判断关于该评论的问题存在?
      Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
      if (dbQuestion == null){
        throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
      }

      // 都存在,回复评论
      commentMapper.insert(comment);

      //设置评论数
      Comment parentComment  = new Comment();
      parentComment.setId(comment.getParentId());
      parentComment.setCommentCount(1);
      //
      commentExtMapper.incCommentCount(parentComment);


      //创建通知
      createNotify(comment,dbComment.getCommentator(),user.getName(),dbQuestion.getTitle(),NotificationTypeEnum.REPLY_COMMRNTS,dbQuestion.getId());
    }else {
      /*=====================是'QUESTION(1)'类型================*/

      //处理要回复的问题不存在情况
      Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
      if (dbQuestion == null){
        throw new CustomizeException(CustomizeErrorCodeEnum.QUESTION_NOT_FOUND);
      }

      // 插入回答
      commentMapper.insert(comment);

      //设置评论量
      dbQuestion.setCommentCount(1);
      //
      questionExtMapper.incCommentCount(dbQuestion);

      //通知
      createNotify(comment,dbQuestion.getCreator(),user.getName(),dbQuestion.getTitle(),NotificationTypeEnum.REPLY_QUESTION,dbQuestion.getId());

    }

  }

  /**
   * 根据标签获得CommentDTO
   * @param id parentId
   * @param commentTypeEnum 回复类型
   * @return list
   */
  public List<CommentDTO> listByTargetId(Long id,CommentTypeEnum commentTypeEnum){
    CommentExample commentExample = new CommentExample();
    commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
    commentExample.setOrderByClause("gmt_create desc");

    // 根据parentId和typ获取Comment集合数据
    List<Comment> commentList = commentMapper.selectByExample(commentExample);

    if (commentList.size() == 0){
      return  new ArrayList<>();
    }

    //获取去重的评论人
    Set<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
    List<Long> userIdList = new LinkedList<>();
    userIdList.addAll(commentators);


    // 将获取去重的评论人集合转换为Map
    UserExample userExample = new UserExample();
    userExample.createCriteria().andIdIn(userIdList);
    List<User> userList = userMapper.selectByExample(userExample);
    Map<Long,User> userMap = userList.stream().collect(Collectors.toMap(user ->user.getId(), user -> user));

    //逐个comment封装成CommentDTO,收集为集合

    List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
      CommentDTO commentDTO = new CommentDTO();
      BeanUtils.copyProperties(comment,commentDTO);
      commentDTO.setUser(userMap.get(comment.getCommentator()));
      return commentDTO;
    }).collect(Collectors.toList());

    return commentDTOList;

  }





  /**
   * 创建一个通知:
   *   回复问题的通知:
   *     该问题的回答 Comment对象
   *     谁回答的问题 user.getName()
   *     通知问题创建者(告诉[他]有人回答你的问题) question.getCreator()
   *
   *
   * @param comment 回复对象
   * @param receiver 接收通知人(告知你发的问题有人回答)
   * @param notifierName 发送通知人(回答的人)
   * @param outerTitle 问题的标题
   * @param notificationType 通知类型(回答了问题/回复了评论)
   * @param outerId 问题id
   */
  private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
    Notification notification = new Notification();
    notification.setGmtCreate(System.currentTimeMillis());
    notification.setType(notificationType.getType());
    notification.setOuterId(outerId);
    notification.setNotifier(comment.getCommentator());
    notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
    notification.setReceiver(receiver);
    notification.setNotifierName(notifierName);
    notification.setOuterTitle(outerTitle);

    notificationMapper.insert(notification);
  }




}
