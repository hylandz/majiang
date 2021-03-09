package com.xlx.majiang.system.controller.oauth;

import com.xlx.majiang.system.dto.oauth.QQAccessTokenDTO;
import com.xlx.majiang.system.dto.oauth.TokenResult;
import com.xlx.majiang.system.entity.User;
import com.xlx.majiang.system.entity.oauth.QQUser;
import com.xlx.majiang.system.provider.QQProvider;
import com.xlx.majiang.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * QQ
 *
 * @author xielx at 2020/3/8 16:53
 */
@Controller
public class QQController {
    
    @Value("${qq.client.id}")
    private String clientId;
    @Value("${qq.client.secret}")
    private String clientSecret;
    @Value("${qq.redirect.uri}")
    private String redirectUri;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Resource
    private QQProvider qqProvider;
    
    @Resource
    private UserService userService;
    /**
     * 调用回调函数
     *
     * @param code  认证后返回的code值,有效期:10min
     * @param state 原state值,校验状态值,防止CSRF攻击
     * @return html
     */
    @GetMapping("/callbackToQQ")
    public String callBack(@RequestParam("code") String code,
                              @RequestParam("state") String state,
                           HttpServletResponse response) {
        logger.info("code={}", code);
        logger.info("state={}", state);
        
        // 参数与Gitee的一致,直接使用
        QQAccessTokenDTO accessTokenDTO = new QQAccessTokenDTO(code, clientId, clientSecret, redirectUri, "authorization_code", "json");
        // 获取Access_Token
        TokenResult result = qqProvider.getAccessToken(accessTokenDTO);
        // 校验token过期
        logger.info("该access token的有效期，单位为秒--------->,{}",result.getExpires_in());
        // openId
        final String openId = qqProvider.getOpenId(result.getAccess_token(),"json");
        // QQ用户信息
        QQUser qqUser = qqProvider.getUserInfo(result.getAccess_token(), clientId, openId);
        if(qqUser != null && qqUser.getRet() == 0) {
            User user = new User();
            user.setAccountId(String.valueOf(clientId));
            user.setName(qqUser.getNickname());
            //
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setBio(openId);
            user.setAvatarUrl(qqUser.getFigureurl_qq_1());
            //
            userService.createOrUpdate(user);
        
            //添加cookie
            response.addCookie(new Cookie("token", token));
        
        }
        return "redirect:/";
    }
    
    
    /**
     * 刷新token
     * @param refreshToken 上次getAccessToken()中获取到的最新的refresh_token。
     * @return 返回的结果
     */
    public TokenResult refreshToken(String refreshToken) {
        // refreshToken是从第一次获取accessToken中返回的
        QQAccessTokenDTO freshToken = new QQAccessTokenDTO(clientId, clientSecret, "refresh_token", "null",refreshToken);
    
        return qqProvider.getAccessToken(freshToken);
    }
}
