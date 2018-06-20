package com.example.arc.model.api;

/**
 * Created by hunghd on 10/3/2017.
 */

/**
 * A generic class represents formatted data of api response.
 *
 */
public class RestData<D> {
    public int code;
    public int status_code;
    public D data;
    public String message;
}
