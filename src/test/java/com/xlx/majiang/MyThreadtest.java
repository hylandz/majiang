package com.xlx.majiang;

/**
 * @author xielx on 2019/7/5
 */
public class MyThreadtest {

  public static void main(String[] args) {
    MyThread t1 = new MyThread("张三","男");
    MyThread t2 = new MyThread("小红","女");
    t1.start();
    t2.start();

  }
}
