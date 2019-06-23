package com.xlx.majiang.mapper;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * mails
 *
 * @author xielx on 2019/6/23
 */
public class CountTime {
  public static void main(String[] args) {
    String[] strArry = {"A","B","C","D"};
    String str1 = Arrays.stream(strArry).collect(Collectors.joining("|"));
    String str2 = Arrays.stream(strArry).collect(Collectors.joining("|","[","]"));
    System.out.println(str1);
    System.out.println(str2);
  }
}
