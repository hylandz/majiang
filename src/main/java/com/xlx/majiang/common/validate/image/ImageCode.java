package com.xlx.majiang.common.validate.image;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图片验证码
 *
 * @author xielx at 2020/2/11 10:34
 */
public class ImageCode {
    
    /**
     * 图片
     */
    private BufferedImage image;
    /**
     * 验证码
     */
    private String code;
    /**
     * 有效时长
     */
    private LocalDateTime expiredTime;
    
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
    
    public ImageCode(BufferedImage image, String code) {
        this.image = image;
        this.code = code;
    }
    
    public ImageCode(BufferedImage image, String code, LocalDateTime expiredTime) {
        this(image,code);
        this.expiredTime = expiredTime;
    }
    
    /**
     * 初始化实例,并设置验证码的有效时长
     * @param image 图片
     * @param code 验证码
     * @param expiredTime 有效时长
     */
    public ImageCode(BufferedImage image, String code, int expiredTime) {
        this(image,code);
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredTime);
    }
    
    /**
     * 判断是否过期
     * @return true:过期
     */
    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiredTime);
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }
    
    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
    
    
    
}
