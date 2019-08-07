package com.xlx.majiang.emailTest;

import com.xlx.majiang.common.util.EmailUtils;

/**
 * 简单邮件
 *
 * @author xielx on 2019/6/27
 */
public class SimpleEmailDemo {


  public static void main(String[] args) {
    String code = EmailUtils.getRandomCode();
    System.out.println("随机码:" + code);
    /*try {
      EmailUtils.sendSimpleEmail("420923119@qq.com",code);

    } catch (EmailException e) {
      e.printStackTrace();
    }
    System.out.println("发送成功");//2188ms*/
  }
}
