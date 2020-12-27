package com.xlx.majiang.system.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 注册
 *
 * @author xielx at 2020/3/3 14:34
 */
@Data
@ToString
public class RegisterDTO extends LoginDTO{
    
    @NotEmpty
    private String phone;
    
}
