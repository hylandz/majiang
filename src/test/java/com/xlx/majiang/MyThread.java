package com.xlx.majiang;

/**
 * 多线程实现
 *
 * @author xielx on 2019/7/5
 */
public class MyThread extends Thread {

  public String name;
  public String sex;

  public MyThread(String name,String sex){
    this.name = name;
    this.sex = sex;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
      for (int i = 0; i < 5; i++) {
        System.out.println(Thread.currentThread().getName() +"," + this.name);
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
