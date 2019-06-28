package com.xlx.majiang.exception;

/**
 * 运行时异常的实例类:
 * 捕捉运行时的异常
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

  public CustomizeException(String message,Throwable throwable){
    super(message,throwable);
  }

  public CustomizeException(String message){
    super(message);
  }
}


