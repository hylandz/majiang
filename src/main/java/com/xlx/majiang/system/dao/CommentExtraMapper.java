package com.xlx.majiang.system.dao;

import com.xlx.majiang.system.entity.Comment;

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
