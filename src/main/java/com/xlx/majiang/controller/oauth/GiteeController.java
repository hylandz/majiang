package com.xlx.majiang.controller.oauth;

import com.xlx.majiang.dto.oauth.GiteeAccessTokenDTO;
import com.xlx.majiang.entity.oauth.GiteeUser;
import com.xlx.majiang.entity.User;
import com.xlx.majiang.provider.GiteeProvider;
import com.xlx.majiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 码云认证
 *
 * @author xielx on 2019/8/5
 */
@Slf4j
@Controller
public class GiteeController {
    
    @Value("${gitee.client.id}")
    private String clientId;
    @Value("${gitee.client.secret}")
    private String clientSecret;
    @Value("${gitee.redirect.uri}")
    private String redirectUri;
    
    @Autowired
    private GiteeProvider giteeProvider;
    @Autowired
    private UserService userService;
    
    /**
     * 回调,先获取密钥,再获取用户信息
     *
     * @param code     code
     * @param response .
     * @return .
     */
    @GetMapping("/callbackToMY")
    public String callbackToMY(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state,
                               HttpServletResponse response) {
        log.info("code={}",code);
        log.info("state={}",state);
        GiteeAccessTokenDTO tokenDTO = new GiteeAccessTokenDTO(code, clientId, clientSecret,redirectUri,"authorization_code",state);
    
        String accessToken = giteeProvider.getAccessToken(tokenDTO);
        log.info("token:[{}]", accessToken);
        
        GiteeUser giteeUser = giteeProvider.getUserInfo(accessToken);
        if (giteeUser != null && giteeUser.getId() != null) {
            String token = UUID.randomUUID().toString();
            User user = new User(String.valueOf(giteeUser.getId()), giteeUser.getName(), token, System.currentTimeMillis(), System.currentTimeMillis(), giteeUser.getBio(), giteeUser.getAvatarUrl());
            //BeanUtils.copyProperties(giteeUser,user);
            
            userService.createOrUpdate(user);
            
            response.addCookie(new Cookie("token", token));
        }
        return "redirect:/";
        
    }
    
    public String refreshToken() {
        // refreshToken是从第一次获取accessToken中返回的
        GiteeAccessTokenDTO refreshToken = new GiteeAccessTokenDTO(clientId, clientSecret, "refresh_token", "");
        
        final String accessToken = giteeProvider.getAccessToken(refreshToken);
        return accessToken;
    }
}
