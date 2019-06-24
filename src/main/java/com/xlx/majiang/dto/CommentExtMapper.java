package com.xlx.majiang.dto;

import com.xlx.majiang.model.Comment;

/**
 * @author: xielx on 2019/6/24
 */
public interface CommentExtMapper {


  /**
   * 评论数量
   * @param comment 评论对象
   * @return int
   */
  int incCommentCount(Comment comment);
}
