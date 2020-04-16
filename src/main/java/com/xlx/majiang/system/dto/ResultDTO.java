package com.xlx.majiang.system.dto;

import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.exception.CustomizeException;
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
  public static <T> ResultDTO<T> errorOf(Integer code, String message) {
    return new ResultDTO<T>(code, message, null);
  }
  public static <T> ResultDTO<T> errorOf() {
    return new ResultDTO<T>(10001, "失败", null);
  }

  /**
   * 系统-异常
   *
   * @param exception 运行异常类对象
   * @return <T>
   */
  public static <T> ResultDTO<T> errorOf(CustomizeException exception) {
    return errorOf(exception.getCode(), exception.getMessage());
  }

  /**
   * 枚举-异常
   *
   * @param errorCodeEnum 枚举,不需要自定义了
   * @return <T>
   */
  public static <T> ResultDTO<T> errorOf(ErrorCodeEnum errorCodeEnum) {
    return errorOf(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
  }
  
  public static <T> ResultDTO<T> errorOf(ErrorCodeEnum errorCodeEnum, T t) {
    return new ResultDTO<T>(errorCodeEnum.getCode(), errorCodeEnum.getMessage(),t);
  }

  /**
   * 成功-默认
   *
   * @return <T>
   */
  public static  <T> ResultDTO<T> okOf() {
    return new ResultDTO<T>(200, "请求成功", null);
  }
  
  /**
   * 成功,显示自定义提示信息
   * @param message 自定义信息
   * @return <T> dto
   */
  public static <T> ResultDTO<T> okOf(String message) {
    return new ResultDTO<T>(200, message, null);
  }

  /**
   * 成功-传参
   * @param t 泛型参数
   * @return <T>
   */
  public static <T> ResultDTO<T> oKOf(T t) {
    return new ResultDTO<T>(200, "请求成功", t);
  }
  
  
  public static <T> ResultDTO<T> oKOf(String message,T t) {
    return new ResultDTO<T>(200, message, t);
  }


}
