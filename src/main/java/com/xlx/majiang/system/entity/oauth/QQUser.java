package com.xlx.majiang.system.entity.oauth;

/**
 * QQ用户信息
 *
 * @author xielx at 2020/3/8 21:03
 */
public class QQUser {

    /**
     * return_code
     */
    private Integer ret;

    /**
     * 信息
     */
    private String msg;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 大小为40x40的QQ头像url
     */
    private String figureurl_qq_1;
    /**
     * 性别,获取不到默认为 "男"
     */
    private String gender;
    
    public Integer getRet() {
        return ret;
    }
    
    public void setRet(Integer ret) {
        this.ret = ret;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }
    
    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
}
