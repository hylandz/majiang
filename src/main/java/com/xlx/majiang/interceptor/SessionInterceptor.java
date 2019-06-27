package com.xlx.majiang.interceptor;

import com.xlx.majiang.mapper.UserMapper;
import com.xlx.majiang.model.User;
import com.xlx.majiang.model.UserExample;
import com.xlx.majiang.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * session拦截器
 *
 * @author xielx on 2019/6/23
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

  @Resource
  private UserMapper userMapper;

  @Resource
  private NotificationService notificationService;
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("拦截器开始工作了~~~~");
    Cookie[] cookies = request.getCookies();
    if(cookies != null && cookies.length != 0){
      for (Cookie cookie : cookies){
        if ("token".equals(cookie.getName())){
          String token = cookie.getValue();
          System.out.println("用cookie取数据了");
          UserExample userExample = new UserExample();
          userExample.createCriteria().andTokenEqualTo(token);

          List<User> userList = userMapper.selectByExample(userExample);
          if (userList.size() != 0){
            request.getSession().setAttribute("user",userList.get(0));
            Long unReadCount = notificationService.unReadCount(userList.get(0).getId());
            request.getSession().setAttribute("unReadCount",unReadCount);
          }
          break;
        }
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }
}
