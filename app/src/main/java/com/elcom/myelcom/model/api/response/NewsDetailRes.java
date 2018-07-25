package com.elcom.myelcom.model.api.response;

import java.util.List;

/**
 * Created by Hailpt on 7/24/2018.
 */
public class NewsDetailRes {

    private int total;
    private List<News> articles;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}
