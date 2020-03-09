package com.xlx.majiang.dao;

import com.xlx.majiang.entity.Comment;

/**
 * @author: xielx on 2019/6/24
 */
public interface CommentExtraMapper {

  /**
   * 累加评论数
   * @param comment
   * @return
   */
  int incCommentCount(Comment comment);
}
