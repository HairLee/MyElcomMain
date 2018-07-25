package com.elcom.myelcom.model.api.request;

/**
 * Created by Hailpt on 7/24/2018.
 */
public class SendCommentReq {

    private int article_id;
    	private String comment_content;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }
}
