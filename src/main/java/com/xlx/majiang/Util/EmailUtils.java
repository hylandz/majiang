package com.xlx.majiang.Util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Random;

/**
 * 邮件工具类
 *
 * @author xielx on 2019/6/26
 */
public class EmailUtils {

  /**
   * 获取4位随机字符
   * @return str
   */
  public static String getRandomCode(){
    //不包括数字0,1和字母"o","i",,"l","O","I"
    String[] codeArray = {"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
            "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z" };

    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 4 ; i++) {
      sb.append(codeArray[random.nextInt(codeArray.length)]);
    }
    return sb.toString();
  }


  /**
   * 获取6位随机数字
   * @return str
   */
  public static String getRandomNumber(){
    Random r = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      sb.append(r.nextInt(10));
    }
    return sb.toString();
  }

  /**
   * 发送纯文本简单邮件
   * @param toEmail 收件人邮箱
   * @param verifyCode 验证码
   * @throws EmailException ex
   */
  public static void sendSimpleEmail(String fromEmail,String toEmail,String verifyCode) throws EmailException {
    long begin = System.currentTimeMillis();
    Email email = new SimpleEmail();
    //设置发送邮件服务器
    email.setHostName("smtp.qq.com");
    email.setSmtpPort(465);
    //
    email.setAuthenticator(new DefaultAuthenticator(fromEmail,"xxnvboshygclhbeb"));

    //使用安全连接
    email.setSSLOnConnect(true);
    //发件人邮箱
    email.setFrom(fromEmail,"MJ社区");
    //收件人邮箱
    email.addTo(toEmail);
    //邮件主题
    email.setSubject("密码重置:");
    //邮件内容
    email.setMsg("尊敬的用户:您好!\n 您的验证码为:" + verifyCode + "\n" + "(有效期为一分钟)");
    email.send();
    long end = System.currentTimeMillis();
    System.out.println("耗时=" + (end-begin));

  }





}
