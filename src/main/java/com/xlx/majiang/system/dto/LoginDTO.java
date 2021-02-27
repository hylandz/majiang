package com.xlx.majiang.system.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 登录
 *
 * @author xielx on 2019/8/5
 */
@Data
public class LoginDTO {

	@NotNull(message = "用户名不为空")
	private String username;
	@NotEmpty(message = "密码不为空")
	private String password;
	
	private Boolean rememberMe;
}
