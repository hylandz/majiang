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

	@NotNull
	private String username;
	@NotEmpty
	private String password;
	
	private Boolean rememberMe;
	@NotEmpty
	private String imageCode;
}
