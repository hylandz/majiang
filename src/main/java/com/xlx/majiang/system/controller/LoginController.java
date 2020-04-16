package com.xlx.majiang.system.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.system.dto.LoginDTO;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.entity.User;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.service.NotificationService;
import com.xlx.majiang.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * login
 *
 * @author xielx at 2020/3/10 10:47
 */
@Controller
public class LoginController {
    
    @Resource
    private UserService userService;
    
    @Resource
    private NotificationService notificationService;
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 登录验证
     *
     * @param request  req
     * @param response res
     * @return dto
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultDTO doLogin(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        
        
        logger.info("接收参数:[{}]", loginDTO);
        //检验参数
        if (loginDTO.getUsername() == null) {
            return ResultDTO.errorOf(ErrorCodeEnum.ACCOUNT_IS_NULL);
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())) {
            return ResultDTO.errorOf(ErrorCodeEnum.CREDENTIALS_IS_NULL);
        }
        
       /* if (loginDTO.getImageCode() == null || !"jetb".equalsIgnoreCase(loginDTO.getImageCode())) {
            //entity.addAttribute("error", "验证码错误");
            return ResultDTO.errorOf(CustomizeErrorCodeEnum.CAPTCHA_WRONG);
        }*/
        
        User user = userService.findUserByPwd(loginDTO.getUsername());
        if (user != null) {
            if (loginDTO.getRememberMe()) {
                //记住我
                Cookie cookie = new Cookie("token", user.getToken());
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);
            } else {
                //不记住
                request.getSession().setAttribute(Constants.USER_SESSION, user);
                Long unReadCount = notificationService.unReadCount(user.getId());
                request.getSession().setAttribute(Constants.UN_READ_COUNT, unReadCount);
            }
            return ResultDTO.okOf();
        }
        
        //entity.addAttribute("error","用户名或密码错误");
        return ResultDTO.errorOf(ErrorCodeEnum.UNAUTHENTICATED);
        
    }
}
