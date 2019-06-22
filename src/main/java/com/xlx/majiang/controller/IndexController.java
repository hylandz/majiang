package com.xlx.majiang.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

  @Autowired
  private UserMapper userMapper;
  /*
   *
   * @param name
   * @return
   */
  @GetMapping("/")
  public String index(HttpServletRequest request){
    // 检测cookie的存在,来实现记住我的功能
    Cookie[] cookies = request.getCookies();

    for (Cookie cookie : cookies){
      System.out.println("key=" + cookie.getName() + ",value=" + cookie.getValue());
      if ("token".equals(cookie.getName())){
        String token = cookie.getValue();
        User user = userMapper.findUserByToken(token);
        if(user != null){
          request.getSession().setAttribute("user",user);
        }
        break;
      }
    }
    return "navigation";
  }

}
