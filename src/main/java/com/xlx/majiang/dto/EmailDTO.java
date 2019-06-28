package com.xlx.majiang.dto;

import lombok.Data;

/**
 * 邮件发送
 *
 * @author xielx on 2019/6/29
 */
@Data
public class EmailDTO {

  //发送邮件的服务器
  private String hostName;
  //smtp的端口
  private String SMTPPort;
  private String userName;
  private String password;

}
