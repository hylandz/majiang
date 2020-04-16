package com.xlx.majiang.system.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * NotificationDTO = Notification + NotificationTypeEnum.typeName
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

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }
}
