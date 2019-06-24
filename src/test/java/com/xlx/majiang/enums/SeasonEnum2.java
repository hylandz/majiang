package com.xlx.majiang.enums;

/**
 * @author: xielx on 2019/6/24
 */
public enum SeasonEnum2 {

  // public static final SeasonEnum2  SPRING = new SeasonEnum2("春天")
  SPRING("春天"),
  SUMMER("夏天"),
  FALL("秋天"),
  WINTER("冬天");

  private String name;

  //默认private
  SeasonEnum2(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  @Override
  public String toString() {
    return "SeasonEnum2{" +
            "name='" + name + '\'' +
            '}';
  }
}
