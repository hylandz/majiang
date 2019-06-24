package com.xlx.majiang.dto;

import lombok.Data;

import java.util.List;

/**
 * TagDTO
 *
 * @author xielx on 2019/6/24
 */
@Data
public class TagDTO {

  private String categoryName;
  private List<String> tags;
}
