package com.xlx.majiang.system.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 帐号类
 */
@Data
@ToString
public class Account implements Serializable {
    
    
    private Long id;

    @NotNull
    private String userName;

    @NotEmpty
    private String password;

    @Email
    private String email;

    private Long gmtCreate;

    private Long gmtModified;

    @NotEmpty
    private String phone;

    private Integer gender;
    
    @Transient
    @NotNull
    private String imageCode;

}