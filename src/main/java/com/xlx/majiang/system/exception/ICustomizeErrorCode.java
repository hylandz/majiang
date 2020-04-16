package com.xlx.majiang.system.exception;

/**
 * 异常结果的处理接口:
 * 提供灵活处理异常方法
 */
public interface ICustomizeErrorCode {

  String getMessage();

  Integer getCode();


}
