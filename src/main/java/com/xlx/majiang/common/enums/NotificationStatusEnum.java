package com.xlx.majiang.demo01.enums;

/**
 * 消息状态枚举
 * @author: xielx on 2019/6/22
 */
public enum NotificationStatusEnum {

  /*未读*/
  UNREAD(0),

  /*已读*/
  READ(1);

  // 状态
  private int status;

  NotificationStatusEnum(int status){
    this.status = status;
  }

  public int getStatus() {
    return status;
  }


}
