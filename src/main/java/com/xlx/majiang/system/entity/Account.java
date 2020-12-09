package com.xlx.majiang.system.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 帐号类
 */
@Data
@ToString
public class Account implements Serializable {
    
    
    private Long id;

    private String userName;

    private String password;

    private String email;

    private Long gmtCreate;

    private Long gmtModified;

    private String phone;

    private Integer gender;

}