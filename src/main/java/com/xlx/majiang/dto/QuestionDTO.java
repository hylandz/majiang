package com.xlx.majiang.dto;

import com.xlx.majiang.model.User;
import lombok.Data;

/**
 * question
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
}
