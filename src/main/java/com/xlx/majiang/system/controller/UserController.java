package com.xlx.majiang.system.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.common.constant.ValidateConstant;
import com.xlx.majiang.common.util.EmailUtils;
import com.xlx.majiang.common.validate.ValidateGenerator;
import com.xlx.majiang.common.validate.image.ImageCode;
import com.xlx.majiang.system.dto.RegisterDTO;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.service.IMailService;
import com.xlx.majiang.system.service.NotificationService;
import com.xlx.majiang.system.service.RedisService;
import com.xlx.majiang.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Value("${spring.mail.password}")
    private String authCode;
    
    @Value("${mail.fromMail.addr}")
    private String from;
    
    @Resource
    private UserService userService;
    
    @Resource
    private NotificationService notificationService;
    
    @Resource
    private IMailService iMailService;
    
    @Resource
    private RedisService redisService;
    
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
     * 发送邮箱验证码
     *
     * @param receiveMail 收件人邮箱
     * @return dto
     */
    @PostMapping("/getCode")
    @ResponseBody
    @ApiOperation(value = "接收的邮箱")
    public ResultDTO getEmailCode(@RequestParam(name = "receiveMail") String receiveMail) {
        logger.info("收件人邮箱:[{}]", receiveMail);
        
        //生成随机验证码
        String code = EmailUtils.getRandomNumber();
        String content = "尊敬的先生/女士:\n您好,验证密码:" + code + ",有效期限:1分钟";
        Long ttl = iMailService.sendSimpleMail(from, receiveMail, "验证", content);
        if (ttl != null && ttl > 0) {
            redisService.setStringEx(Constants.EMAIL_CODE, code, 60L);
            logger.info("redis的 key----[{}]", redisService.getStringValue(Constants.EMAIL_CODE));
            return ResultDTO.okOf();
        } else {
            
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_SEND_FAILED);
        }
    }
    
    /**
     * 验证码验证
     *
     * @param emailCode 接收的验证码
     * @return .
     */
    @PostMapping("/emailAuth")
    @ResponseBody
    public ResultDTO emailAuthorized(@RequestParam("emailCode") String emailCode) {
        logger.info("验证码:[{}]", emailCode);
        //校验参数
        if (StringUtils.isEmpty(emailCode)) {
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_CODE_IS_NULL);
        }
        
        Long ttl = redisService.getStringTTL(Constants.EMAIL_CODE);
        String code = redisService.getStringValue(Constants.EMAIL_CODE);
        
        logger.info("ttl=[{}],code=[{}]", ttl, code);
        //失效
        if (ttl < 0 || code == null) {
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_CODE_IS_NOT_AVAILABLE);
        }
        
        if (emailCode.equals(code)) {
            
            return ResultDTO.okOf();
        }
        
        return ResultDTO.errorOf(ErrorCodeEnum.CAPTCHA_WRONG);
    }
    
    
    
    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public ResultDTO doRegister(RegisterDTO registerDTO) {
        logger.info("前台注册参数:{}", registerDTO);
        return ResultDTO.oKOf(registerDTO);
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
