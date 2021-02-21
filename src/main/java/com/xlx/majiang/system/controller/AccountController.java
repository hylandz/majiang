package com.xlx.majiang.system.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.common.constant.ValidateConstant;
import com.xlx.majiang.common.util.EmailUtils;
import com.xlx.majiang.common.validate.ValidateGenerator;
import com.xlx.majiang.common.validate.image.ImageCode;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.entity.Account;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.service.AccountService;
import com.xlx.majiang.system.service.IMailService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Account
 *
 * @author xielx at 2021/2/19 17:36
 */
@Controller
@Validated
public class AccountController {
    
    @Value("${spring.mail.password}")
    private String authCode;
    
    @Value("${mail.fromMail.addr}")
    private String from;
    
    @Resource
    private IMailService iMailService;
    
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Resource
    private AccountService accountService;
    
    @Autowired
    private ValidateGenerator imageCodeGenerator;
    
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
    public ResultDTO getEmailCode(@RequestParam(name = "receiveMail") String receiveMail, HttpServletRequest request) {
        logger.info("收件人邮箱:[{}]", receiveMail);
        
        //生成随机验证码
        String code = EmailUtils.getRandomNumber();
        String content = "尊敬的先生/女士:\n您好,验证密码:" + code + ",有效期限:1分钟";
        try {
            iMailService.sendSimpleMail(from, receiveMail, "验证", content);
            HttpSession session = request.getSession();
            session.setAttribute(Constants.EMAIL_CODE,code);
            session.setAttribute("createTime",System.currentTimeMillis());
            return ResultDTO.okOf();
        }catch (MailException m){
            logger.error("邮件发送异常:[{}]",m.getMessage());
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_SEND_FAILED);
        }
    }
    
    /**
     * 修改密码
     *
     * @param emailCode 请求数据
     * @param password 请求数据
     * @return .
     */
    @PostMapping("/emailAuth")
    @ResponseBody
    public ResultDTO emailAuthorized(@RequestParam String emailCode,@RequestParam String password, HttpServletRequest request) {
        //校验参数
        /*if (StringUtils.isEmpty(account.getEmailCode())) {
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_CODE_IS_NULL);
        }*/
        HttpSession session = request.getSession();
        long createTime = (long) session.getAttribute("createTime");
        String code = (String) session.getAttribute(Constants.EMAIL_CODE);
        if (System.currentTimeMillis() - createTime < 5 * 60 * 1000){// 5分钟内
            if(Objects.equals(emailCode,code)){
                // 修改密码
                accountService.changeAccountPwd(password);
                session.removeAttribute(Constants.EMAIL_CODE);
                return ResultDTO.okOf("密码修改成功");
            }else {
                return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_CODE_IS_NOT_AVAILABLE);
            }
        }else {
    
            return ResultDTO.errorOf(ErrorCodeEnum.EMAIL_CODE_INVALID);
        }
    
    }
    
    /**
     * 用户注册
     *
     * @return ResultDTO
     *
     */
    @PostMapping("/user/register")
    @ResponseBody
    public ResultDTO doRegister(@Validated Account account,HttpServletRequest request) {
        logger.info("前台注册参数:{}", account);
       String validateCode = (String) request.getSession().getAttribute((ValidateConstant.SESSION_KEY));
       if (Objects.equals(account.getImageCode(),validateCode)){// 验证码匹配
           return accountService.registerAccount(account);
       }
        logger.info("{},注冊失败", LocalDateTime.now());
       return ResultDTO.errorOf(ErrorCodeEnum.REGISTER_FAIL);
    }
    
    
}
