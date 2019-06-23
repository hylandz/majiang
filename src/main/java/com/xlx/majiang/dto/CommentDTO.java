package com.xlx.majiang.dto;

import com.xlx.majiang.model.User;
import lombok.Data;

/**
 * comment:dto
 *
 * @author xielx on 2019/6/24
 */
@Data
public class CommentDTO {

  private Long id;
  private Long parentId;
  private Integer type;
  private Long commentator;
  private Long gmtCreate;
  private Long gmtModified;
  private Long likeCount;
  private Integer commentCount;
  private String content;

  
  private User user;
}
