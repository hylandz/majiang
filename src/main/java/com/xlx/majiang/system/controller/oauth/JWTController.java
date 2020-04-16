package com.xlx.majiang.system.controller.oauth;

import com.xlx.majiang.common.util.JWTUtil;
import com.xlx.majiang.system.dto.LoginDTO;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * JWT
 *
 * @author xielx at 2020/4/16 10:09
 */
@RestController
@Slf4j
public class JWTController {
    
    @PostMapping("/api/login")
    public ResultDTO<String> jwtLogin(@RequestBody LoginDTO loginDTO){
        log.info("LoginDTO={}",loginDTO);
        if (loginDTO != null && Objects.equals("1234",loginDTO.getPassword())){
            String token = JWTUtil.generateToken(loginDTO.getUsername(), LocalDateTime.now());
            return ResultDTO.oKOf("登录成功",token);
        }
        return ResultDTO.errorOf();
    }
    
    @PostMapping("api/validate")
    public ResultDTO<Boolean> verifySignature(String token){
        return JWTUtil.validate(token) ? ResultDTO.okOf("token有效") : ResultDTO.errorOf(ErrorCodeEnum.JWT_TOKEN_UNVALIDATED);
    }
    
    @GetMapping("/api/fresh")
    public ResultDTO<String> freshToken(String username,LocalDateTime time){
        String freshToken = JWTUtil.generateToken(username, LocalDateTime.now());
        return ResultDTO.oKOf("刷新token",freshToken);
    }
    
}
