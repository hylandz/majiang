package com.xlx.majiang.mapper;

import com.xlx.majiang.model.Comment;

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
