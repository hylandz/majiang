package com.xlx.majiang.dto;

import lombok.Data;

/**
 * CommentCreateDTO
 *
 * @author xielx on 2019/6/24
 */
@Data
public class CommentCreateDTO {
  private Long parentId;
  private String content;
  private Integer type;
}
