package com.dk.entity;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer vedioId;

    private Integer userId;

    private String comment;

    private String userName;

    private Byte isReply;

    private String replyedUserName;

    private Integer replyedUserId;

    private String avatarUrl;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVedioId() {
        return vedioId;
    }

    public void setVedioId(Integer vedioId) {
        this.vedioId = vedioId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getIsReply() {
        return isReply;
    }

    public void setIsReply(Byte isReply) {
        this.isReply = isReply;
    }

    public String getReplyedUserName() {
        return replyedUserName;
    }

    public void setReplyedUserName(String replyedUserName) {
        this.replyedUserName = replyedUserName == null ? null : replyedUserName.trim();
    }

    public Integer getReplyedUserId() {
        return replyedUserId;
    }

    public void setReplyedUserId(Integer replyedUserId) {
        this.replyedUserId = replyedUserId;
    }
}