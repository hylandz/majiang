package com.xlx.majiang.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * mails
 *
 * @author xielx on 2019/6/23
 */
public class StreamTest {



  public static void main(String[] args) {
    String[] strArray = {"A","B","C","D"};
    String str1 = Arrays.stream(strArray).collect(Collectors.joining("|"));
    String str2 = Arrays.stream(strArray).collect(Collectors.joining("|","[","]"));
    System.out.println(str1);
    System.out.println(str2);


    System.out.println("========================================");
      Object o=new Object(){
        //重写了equals(),不管参数是什么，都是返回true
        public boolean equals(Object obj){
          return true;
        }
      };
      System.out.println(o.equals("Fred"));
  }

}
