package com.elcom.myelcom.model.api.response;

import java.util.List;

/**
 * Created by Hailpt on 7/24/2018.
 */
public class NewsNormal {
    private int category_id;
    private String category_name;
    private List<News> articles;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}
