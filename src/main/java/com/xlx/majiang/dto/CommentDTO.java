package com.xlx.majiang.dto;

import com.xlx.majiang.model.User;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * comment:dto
 *
 * @author xielx on 2019/6/24
 */
@Data
public class CommentDTO {

  //Comment对象
  private Long id;
  private Long parentId;
  private Integer type;
  private Long commentator;
  private Long gmtCreate;
  private Long gmtModified;
  private Long likeCount;
  private Integer commentCount;
  private String content;

 // private String parentComment = null;
 // User 对象
  private User user;

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }
}
