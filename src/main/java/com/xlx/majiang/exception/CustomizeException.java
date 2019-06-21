package com.xlx.majiang.exception;

/**
 * 自定义异常
 *
 * @Author xielx on 2019/6/21
 */
public class CustomizeException extends RuntimeException{


  //状态码
  private Integer code;
  //信息
  private String message;


  public CustomizeException(ICustomizeErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}


