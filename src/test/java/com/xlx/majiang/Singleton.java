package com.xlx.majiang;

/**
 * 单例模式
 *
 * @author xielx on 2019/7/5
 */
public class Singleton {

  //  懒汉模式:线程不安全
 /* private static Singleton singleton;
  private Singleton(){}

  public static Singleton getSingleton(){
    if (singleton == null){
      singleton = new Singleton();
    }
    return singleton;
  }*/


  //恶汉模式:线程安全
  private static Singleton singleton = new Singleton();
  private Singleton(){}
  public static Singleton getSingleton(){
    return singleton;
  }


}
