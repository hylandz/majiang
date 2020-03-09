package com.xlx.majiang.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 消息通知
 * 用户A:发布问题Q1"离职后来怎样?"
 * 用户B看见并回答"不怎样"(同时,通知A:用户B回答你的问题Q1"离职后来怎样)
 * 用户A看见B的回答,回复到,"滚"(同时,通知B:用户A回复了你的评论)
 *
 */
public class Notification implements Serializable {
    private Long id; // 主键

    private Long notifier; //通知人(外键,User表id)

    private Long receiver; //接收人/回答别人的那个人(外键,User表id)

    private Long outerId; // 该问题id

    private Integer type; // 通知类型,回答了问题还是回复了评论

    private Integer status; // 通知状态,没点开看就是未读0,点开看就变成已读1

    private String notifierName; //通知人的名字,(User表的name)

    private String outerTitle; //问题标题,如回复问题"离职后来怎样"

    private Long gmtCreate;

    private Long gmtModified;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotifier() {
        return notifier;
    }

    public void setNotifier(Long notifier) {
        this.notifier = notifier;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getOuterId() {
        return outerId;
    }

    public void setOuterId(Long outerId) {
        this.outerId = outerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName == null ? null : notifierName.trim();
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}