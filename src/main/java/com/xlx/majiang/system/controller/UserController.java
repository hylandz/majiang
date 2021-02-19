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
    
    @Autowired
    private ValidateGenerator imageCodeGenerator;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    
    /**
     * 生成图片验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/image")
    @ResponseBody
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) {
        // 设置响应信息
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");
    
        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
        logger.info("图片验证码:[{}]", imageCode.getCode());
        request.getSession().setAttribute(ValidateConstant.SESSION_KEY, imageCode);
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            logger.error("验证码写出失败:{}",e.getMessage());
        }
    }
    
    
    
    
    
    
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
