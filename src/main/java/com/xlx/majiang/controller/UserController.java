package com.xlx.majiang.controller;

import com.xlx.majiang.cache.Constants;
import com.xlx.majiang.dto.LoginDTO;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.NotificationService;
import com.xlx.majiang.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * user
 *
 * @author xielx on 2019/6/25
 */
@Controller
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Resource
  private UserService userService;

  @Resource
  private NotificationService notificationService;

  /**
   * 跳转登录页面
   * @return .
   */
  @GetMapping("/login")
  public String login(){
    return "login";
  }

  /**
   * 跳转忘记密码页面
   */
  @GetMapping("/forgetPwd")
  public String forgetPassword(){
    return "forgetPwd";
  }

  /**
   * 登录验证
   * @param request req
   * @param response res
   * @return dto
   */
  @ResponseBody
  @PostMapping("/login")
  public ResultDTO doLogin(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){


    logger.info("接收参数:[{}]",loginDTO);
    //检验参数
    if (loginDTO.getUsername() == null) {
      //model.addAttribute("error", "用户名不能为空");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.ACCOUNT_IS_NULL);
    }
    if (StringUtils.isEmpty(loginDTO.getPassword())) {
      //model.addAttribute("error", "密码不能为空");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.CREDENTIALS_IS_NULL);
    }

    if (loginDTO.getCaptcha() == null || !"jetb".equalsIgnoreCase(loginDTO.getCaptcha())) {
      //model.addAttribute("error", "验证码错误");
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.CAPTCHA_WRONG);
    }

    User user =userService.findUserByPwd(loginDTO.getUsername());
    if (user != null){
      if(loginDTO.getRememberMe()){
        //记住我
        Cookie cookie = new Cookie("token", user.getToken());
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResultDTO.okOf();
      }else {
        //不记住
        request.getSession().setAttribute(Constants.USER_SESSION,user);
        Long unReadCount = notificationService.unReadCount(user.getId());
        request.getSession().setAttribute(Constants.UN_READ_COUNT,unReadCount);
        return ResultDTO.okOf();
      }

    }

    //model.addAttribute("error","用户名或密码错误");
    return ResultDTO.errorOf(CustomizeErrorCodeEnum.UNAUTHENTICATED);

  }

  /**
   * 注销
   * @param request .
   * @param response .
   * @return .
   */
  @GetMapping("/logout")
  public String logout(HttpServletRequest request,HttpServletResponse response){
    // request.getSession().invalidate();
    request.getSession().removeAttribute(Constants.USER_SESSION);
    Cookie cookie = new Cookie("token",null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    return "redirect:/";
  }

}
