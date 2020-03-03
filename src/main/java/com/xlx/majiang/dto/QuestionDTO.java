package com.xlx.majiang.dto;

import com.xlx.majiang.model.User;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * QuestionDTO = question + user
 *
 * @author xielx on 2019/6/23
 */
@Data
public class QuestionDTO {

  //Question对象
  private Long id;
  private String title;
  private String description;
  private String tag;
  private Long gmtCreate;
  private Long gmtModified;
  private Long creator;
  private Integer viewCount;
  private Integer commentCount;
  private Integer likeCount;

  //User对象
  private User user;
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }
}
