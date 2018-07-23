package com.elcom.hailpt.model.api.response;

import java.util.List;

/**
 * Created by Hailpt on 7/23/2018.
 */
public class NewsRes {

    private List<News> normal_articles;
    private List<News> hot_articles;

    public List<News> getNormal_articles() {
        return normal_articles;
    }

    public void setNormal_articles(List<News> normal_articles) {
        this.normal_articles = normal_articles;
    }

    public List<News> getHot_articles() {
        return hot_articles;
    }

    public void setHot_articles(List<News> hot_articles) {
        this.hot_articles = hot_articles;
    }
}
