package com.elcom.hailpt.model.api.response;

/**
 * Created by Hailpt on 7/23/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("modified_at")
    @Expose
    private Object modifiedAt;
    @SerializedName("hot_article")
    @Expose
    private Integer hotArticle;
    @SerializedName("category_name")
    @Expose
    private String categoryName;

    @SerializedName("sum_like")
    @Expose
    private Integer sum_like;

    @SerializedName("status_vote")
    @Expose
    private Integer status_vote;

    @SerializedName("sum_comment")
    @Expose
    private Integer sum_comment;

    @SerializedName("comment")
    @Expose
    private List<Comment> comment;

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Object getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getHotArticle() {
        return hotArticle;
    }

    public void setHotArticle(Integer hotArticle) {
        this.hotArticle = hotArticle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public Integer getSum_like() {
        return sum_like;
    }

    public void setSum_like(Integer sum_like) {
        this.sum_like = sum_like;
    }

    public Integer getStatus_vote() {
        return status_vote;
    }

    public void setStatus_vote(Integer status_vote) {
        this.status_vote = status_vote;
    }

    public Integer getSum_comment() {
        return sum_comment;
    }

    public void setSum_comment(Integer sum_comment) {
        this.sum_comment = sum_comment;
    }
}
