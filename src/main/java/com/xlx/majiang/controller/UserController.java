package com.xlx.majiang.controller;

import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.NotificationService;
import com.xlx.majiang.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * user
 *
 * @author xielx on 2019/6/25
 */
@Controller
public class UserController {


  @Resource
  private UserService userService;

  @Resource
  private NotificationService notificationService;

  @GetMapping("/login")
  public String login(){
    return "login";
  }

  @ResponseBody
  @PostMapping("/login")
  public ResultDTO doLogin(@RequestParam(name = "username") String userName,
                           @RequestParam(name = "password") String userPassword,
                           @RequestParam(name = "captcha",required = false) String captcha,
                           @RequestParam(name="rememberMe") boolean rememberMe,
                           HttpServletRequest request,
                           HttpServletResponse response){

    //检验参数
    if (captcha == null || "jteb".equalsIgnoreCase(captcha)) {
      //model.addAttribute("error", "验证码错误");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.CAPTCHA_WRONG);
    }
    if (userName == null || userName == "") {
      //model.addAttribute("error", "用户名不能为空");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.UNAUTHENTICATED);
    }
    if (userPassword == null || userPassword == "") {
      //model.addAttribute("error", "密码不能为空");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.UNAUTHENTICATED);
    }


    User user =userService.login(userPassword);

    if (user != null){
      if(rememberMe){
        //记住我
        response.addCookie(new Cookie("token", UUID.randomUUID().toString()));
        return ResultDTO.okOf();
      }else {
        //不记住
        request.getSession().setAttribute("user",user);
        Long unReadCount = notificationService.unReadCount(user.getId());
        request.getSession().setAttribute("unReadCount",unReadCount);
        return ResultDTO.okOf();
      }

    }

    //model.addAttribute("error","用户名或密码错误");
    return ResultDTO.errorOf(CustomizeErrorCodeEnum.UNAUTHENTICATED);

  }

}
