package com.xlx.majiang.common.enums;

/**
 * 评论类型枚举:
 * 1.提问
 * 2.评论
 */
public enum CommentTypeEnum {

  /*提问*/
  QUESTION(1),
  /*评论*/
  COMMENT(2);

  // 类型
  private Integer type;

  CommentTypeEnum(Integer type){
    this.type = type;
  }

  public Integer getType() {
    return type;
  }

  /**
   * 判断评论类型是否存在
   * @param type 评论类型
   * @return
   */
  public static boolean isExists(Integer type){
    for(CommentTypeEnum cte : CommentTypeEnum.values()){
      if(cte.getType() == type){
        return true;
      }
    }
    return false;
  }
}
