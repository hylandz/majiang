package com.xlx.majiang.system.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.common.constant.ValidateConstant;
import com.xlx.majiang.common.validate.ValidateGenerator;
import com.xlx.majiang.common.validate.image.ImageCode;
import com.xlx.majiang.system.service.NotificationService;
import com.xlx.majiang.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    
    
    /*@Resource
    private RedisService redisService;*/
    
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    
    
    
    
    
    
    
    /**
     * 注销
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // request.getSession().invalidate();
        request.getSession().removeAttribute(Constants.USER_SESSION);
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
    
}
