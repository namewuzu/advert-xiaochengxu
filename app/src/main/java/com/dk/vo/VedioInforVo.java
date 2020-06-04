package com.dk.vo;

import java.util.List;

/**
 * Created by wuzu on 2019/4/23.
 */
public class VedioInforVo extends VedioVo{

    private Integer pointId;

    private Integer collectId;

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    private List<CommentVo>  comments ;

    public List<CommentVo> getComments() {
        return comments;
    }

    public void setComments(List<CommentVo> comments) {
        this.comments = comments;
    }
}
