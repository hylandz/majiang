package com.xlx.majiang.controller;

import com.xlx.majiang.common.cache.Constants;
import com.xlx.majiang.dto.NotificationDTO;
import com.xlx.majiang.enums.NotificationTypeEnum;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 通知
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class NotificationController {

  @Resource
  private NotificationService notificationService;


  /**
   * 点击查看通知
   * @param id 通知id
   * @param request re
   * @return String
   */
  @GetMapping("/notification/{id}")
  public String profile(@PathVariable("id") Long id, HttpServletRequest request) {
    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
    if (user == null) {
      return "redirect:/";
    }

    NotificationDTO notificationDTO = notificationService.read(id, user);
    boolean flag = NotificationTypeEnum.REPLY_COMMRNTS.getType() == notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType();
    if (flag){
      return "redirect:/question/" + notificationDTO.getOuterId();
    }else {
      return "redirect:/";
    }
  }
}
