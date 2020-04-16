package com.xlx.majiang.system.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 注册
 *
 * @author xielx at 2020/3/3 14:34
 */
@Data
@ToString
public class RegisterDTO extends LoginDTO{
    
    private String phone;
    
}
