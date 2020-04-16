package com.xlx.majiang.system.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 实体类:User-用户
 * @author xielx
 */
@Data
public class User implements Serializable {

    private Long id;// 主键

    private String accountId; // 第三方账户id

    private String name; // 第三方账户姓名

    private String token; // token,当作cookie

    private Long gmtCreate; // 创建时间

    private Long gmtModified; // 修改时间

    private String bio; // 第三方账户个性签名

    private String avatarUrl; // 第三方账户昵称头像


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    public User(){}

    public User(String accountId, String name, String token, Long gmtCreate, Long gmtModified, String bio, String avatarUrl) {
        this.accountId = accountId;
        this.name = name;
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }
}