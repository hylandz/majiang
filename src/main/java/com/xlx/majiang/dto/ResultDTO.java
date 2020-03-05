package com.xlx.majiang.dto;

import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import lombok.Data;

/**
 * 结果类:
 * 成功和失败时显示结果信息
 * @author xielx on 2019/6/21
 */
@Data
public class ResultDTO<T> {

  // http状态码
  private Integer code;

  //信息
  private String message;

  //泛型参数
  private T data;


  public ResultDTO(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }


  /**
   * 异常
   *
   * @param code    状态码
   * @param message 信息
   * @return dto
   */
  public static ResultDTO errorOf(Integer code, String message) {
    return new ResultDTO(code, message, null);
  }


  /**
   * 系统-异常
   *
   * @param exception 运行异常类对象
   */
  public static ResultDTO errorOf(CustomizeException exception) {
    return errorOf(exception.getCode(), exception.getMessage());
  }

  /**
   * 枚举-异常
   *
   * @param errorCodeEnum 枚举,不需要自定义了
   * @return
   */
  public static ResultDTO errorOf(CustomizeErrorCodeEnum errorCodeEnum) {
    return errorOf(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
  }
  
  public static <T> ResultDTO errorOf(CustomizeErrorCodeEnum errorCodeEnum,T t) {
    return new ResultDTO(errorCodeEnum.getCode(), errorCodeEnum.getMessage(),t);
  }

  /**
   * 成功-默认
   *
   * @return
   */
  public static ResultDTO okOf() {
    return new ResultDTO(200, "请求成功", null);
  }
  
  /**
   * 成功,显示自定义提示信息
   * @param message 自定义信息
   * @return dto
   */
  public static ResultDTO okOf(String message) {
    return new ResultDTO(200, message, null);
  }

  /**
   * 成功-传参
   * @param t 泛型参数
   * @return
   */
  public static <T> ResultDTO oKOf(T t) {
    return new ResultDTO(200, "请求成功", t);
  }


}
