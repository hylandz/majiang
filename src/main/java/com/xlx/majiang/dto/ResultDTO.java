package com.xlx.majiang.dto;

import lombok.Data;

/**
 * 结果类
 *
 * @Author xielx on 2019/6/21
 */
@Data
public class ResultDTO <T>{

  // http状态码
  private Integer code;

  //信息
  private String message;

  //参数
  private T data;



}
