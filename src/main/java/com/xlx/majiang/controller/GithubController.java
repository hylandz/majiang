package com.xlx.majiang.controller;

import com.xlx.majiang.common.util.EmailUtils;
import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.dto.AccessTokenDTO;
import com.xlx.majiang.dto.GitHubUser;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.model.User;
import com.xlx.majiang.provider.GitHubProvider;
import com.xlx.majiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * github认证
 *
 * @author xielx on  2019/6/21
 * To change this template use File | Settings | Editor | File and Code Templates.
 */
@Slf4j
@Controller
public class GithubController {

  @Value("${github.client.id}")
  private String clientId;
  @Value("${github.client.secret}")
  private String clientSecret;
  @Value("${github.redirect.uri}")
  private String redirectUri;

  @Value("${email.from}")
  private String fromEmail;
  @Value("${email.authorized.code}")
  private String authCode;

  @Resource
  private GitHubProvider gitHubProvider;

  @Resource
  private UserService userService;




  /**
   *  github认证后,获取github用户信息
   * @param code .
   * @param state .
   * @param response .
   * @return str
   */
  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {

    AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId,clientSecret,code,redirectUri,state);

    String  accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
    GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
    if(gitHubUser != null && gitHubUser.getId() != null){
      User user = new User();
      user.setAccountId(String.valueOf(gitHubUser.getId()));
      user.setName(gitHubUser.getName());
      //
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      user.setBio(gitHubUser.getBio());
      user.setAvatarUrl(gitHubUser.getAvatarUrl());
      //
      userService.createOrUpdate(user);

      //添加cookie
      response.addCookie(new Cookie("token",token));

      return "redirect:/";
    }else {
      //登录失败,重写登录
      return "redirect:/";
    }
  }







  /**
   * 获取验证码
   * @param emailName 收件人邮箱
   * @param request re
   * @return dto
   */
  @ResponseBody
  @PostMapping("/getCode")
  public ResultDTO getEmailCode(@RequestParam(name = "emailName") String emailName, HttpServletRequest request){
    log.info("收件人邮箱:[{}]",emailName);

    //生成随机验证码
    String code = EmailUtils.getRandomNumber();

    try {
      EmailUtils.sendSimpleEmail(fromEmail,emailName,code,authCode);
    } catch (EmailException e) {
      return ResultDTO.errorOf(2019,e.getMessage());
    }
    HttpSession session = request.getSession();
    session.setAttribute(Constants.EMAIL_CODE,code);
    session.setAttribute("start",System.currentTimeMillis());
    session.setMaxInactiveInterval(120);//unit/second
    return ResultDTO.okOf();
  }

  /**
   * 验证码验证
   * @param emailCode 接收的验证码
   * @return .
   */
  @ResponseBody
  @PostMapping("/emailAuth")
  public ResultDTO emailAuthorized(@RequestParam("emailCode") String emailCode,
                                   HttpServletRequest request){
    System.out.println("验证码:" + emailCode);

    //校验参数
    if (StringUtils.isEmpty(emailCode)){
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.EMAIL_CODE_IS_NULL);
    }

    String code = (String) request.getSession().getAttribute(Constants.EMAIL_CODE);
    long start = (long) request.getSession().getAttribute("start");

    long result = System.currentTimeMillis() - start;
    //2min内有效
    if (result >= 120000 || !emailCode.equalsIgnoreCase(code)){
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.EMAIL_CODE_IS_NOT_AVAILABLE);
    }

    // request.getSession().removeAttribute(Constants.EMAIL_CODE);
    return ResultDTO.okOf();
  }


}
