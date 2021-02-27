package com.xlx.majiang.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 注册
 *
 * @author xielx at 2020/3/3 14:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class RegisterDTO extends LoginDTO{
    
    @NotEmpty
    private String phone;
    @NotEmpty
    private String imageCode;
    
}
