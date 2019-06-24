package com.xlx.majiang.enums;

/**
 * test
 *
 * @author xielx on 2019/6/24
 */
public class EnumTest {


  public void judge(SeasonEnum season){
    switch (season){

      case SPRING:
        System.out.println("春天到了,万物复苏");
        break;
      case SUMMER:
        System.out.println("夏天到了,知了叫了");
        break;
      case FALL:
        System.out.println("秋天到了,丰收的季节");
        break;
      case WINTER:
        System.out.println("冬天的到了,下雪了");
        break;
    }
  }

  public static void main(String[] args) {
    for (SeasonEnum seasonEnum :SeasonEnum.values()) {
      System.out.println("1212:"+seasonEnum);
    }

   // new EnumTest().judge(SeasonEnum.SPRING);
    System.out.println(SeasonEnum2.FALL.toString());

  }
}
