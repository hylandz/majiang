package com.xlx.majiang.dto;

import lombok.Data;

/**
 * NotificationDTO
 *
 * @author xielx on 2019/6/23
 */
@Data
public class NotificationDTO {

  //Notification对象
  private Long id;
  private Long gmtCreate;
  private Integer status;
  private Long notifier;
  private String notifierName;
  private String outerTitle;
  private Long outerId;
  private Integer type;

  //
  private String typeName;
}
