package com.xlx.majiang.exception;

/**
 * 异常枚举:
 * 提供各种自定义异常
 * @author xielx on date 2019/6/22
 **/
public enum CustomizeErrorCodeEnum implements  ICustomizeErrorCode{


  QUESTION_NOT_FOUND(2001,"你找到的问题不在了,换个试试?"),
  TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
  NOT_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
  SYS_ERROR(2004, "服务冒烟了，休息一下再试试！！！"),
  TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
  COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
  CONTENTS_IS_EMPTY(2007, "输入内容不能为空"),
  READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
  NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
  UNAUTHENTICATED(2010,"用户名或密码错误"),
  CAPTCHA_WRONG(2011,"验证码错误"),
  ACCOUNT_IS_NULL(2012,"用户名不能为空"),
  CREDENTIALS_IS_NULL(2013,"密码不能为空"),

  EMAIL_IS_NULL(2014,"邮箱名称不能为空"),
  EMAIL_FORMAT_WRONG(2015,"邮箱格式错误"),
  EMAIL_CODE_IS_NULL(2016,"验证码不能为空"),
  EMAIL_CODE_IS_NOT_AVAILABLE(2017,"验证错误");

  private Integer code;

  private String message;


  CustomizeErrorCodeEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public Integer getCode() {
    return code;
  }
}
