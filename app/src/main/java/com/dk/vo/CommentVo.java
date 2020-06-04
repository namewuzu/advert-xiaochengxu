package com.dk.vo;

import com.mysql.fabric.xmlrpc.base.Data;

import java.util.Date;

/**
 * Created by wuzu on 2019/4/23.
 */
public class CommentVo {

    private Integer id;

    private String Comment;

    private String userName;

    private Byte isReply;//是否回复(1-是，2-否)

    private String replyedUserName;//被回复的人姓名

    private Integer replyedUserId;

    private Integer userId;

    private String avatarUrl;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getIsReply() {
        return isReply;
    }

    public void setIsReply(Byte isReply) {
        this.isReply = isReply;
    }

    public Integer getReplyedUserId() {
        return replyedUserId;
    }

    public void setReplyedUserId(Integer replyedUserId) {
        this.replyedUserId = replyedUserId;
    }

    public String getReplyedUserName() {
        return replyedUserName;
    }

    public void setReplyedUserName(String replyedUserName) {
        this.replyedUserName = replyedUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
