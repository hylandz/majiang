package com.xlx.majiang.demo01.enums;

/**
 * (消息)回复类型枚举
 * @author: xielx on 2019/6/22
 */
public enum NotificationTypeEnum {

  /*回答了问题*/
  REPLY_QUESTION(1,"回复了问题"),

  /*回复了评论*/
  REPLY_COMMRNTS(2,"回复了评论");

  // 回复类型
  private int type;
  // 回复名称
  private String name;

  NotificationTypeEnum(int type,String name){
    this.type = type;
    this.name = name;

  }

  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  /**
   * 根据回复类型获取对应的回复名称
   * @param type 回复类型
   * @return 回复名称
   */
  public static String nameOfType(int type){
    for(NotificationTypeEnum nte : NotificationTypeEnum.values()){
      if(nte.type == type){
        return nte.getName();
      }
    }
    return "";
  }
}
