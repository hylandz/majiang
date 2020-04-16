package com.xlx.majiang.system.dto;

import lombok.Data;

import java.util.List;

/**
 * 标签:Tag
 * @author xielx on 2019/6/24
 */
@Data
public class TagDTO {

  /*目录名称*/
  private String categoryName;

  /*标签集合*/
  private List<String> tags;
}
