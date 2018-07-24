package com.elcom.hailpt.model.api.request;

/**
 * Created by Hailpt on 7/24/2018.
 */
public class NewsDetailRq {

    private int id;
    private int offset;
    private int limit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
