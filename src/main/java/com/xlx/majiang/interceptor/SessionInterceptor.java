package com.xlx.majiang.common.interceptor;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.demo01.mapper.UserMapper;
import com.xlx.majiang.model.User;
import com.xlx.majiang.model.UserExample;
import com.xlx.majiang.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * session拦截器:
 * <p>
 * 关键还是需要登录才能操作的功能要挡,跳转登录/首页
 * 不用登录也能操作的就不拦截
 *
 * @author xielx on 2019/6/23
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

	@Resource
	private UserMapper userMapper;

	@Resource
	private NotificationService notificationService;

	@Value("${github.redirect.uri}")
	private String githubUri;

	@Value("${gitee.redirect.uri}")
	private String giteeUri;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.getServletContext().setAttribute("githubUri", githubUri);
		request.getServletContext().setAttribute("giteeUri", giteeUri);


		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();

					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);

					List<User> userList = userMapper.selectByExample(userExample);
					if (userList.size() != 0) {
						request.getSession().setAttribute(Constants.USER_SESSION, userList.get(0));
						Long unReadCount = notificationService.unReadCount(userList.get(0).getId());
						request.getSession().setAttribute(Constants.UN_READ_COUNT, unReadCount);
					}
					break;
				}
			}
		}

    /*User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
    if ( user!= null){
      Long unReadCount = notificationService.unReadCount(user.getId());
      request.getSession().setAttribute(Constants.UN_READ_COUNT,unReadCount);
      return true;
    }

    response.sendRedirect("/");*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
