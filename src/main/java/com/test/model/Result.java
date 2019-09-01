package com.test.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

public class Result {
    //响应码
    //200 success
    //400 fail

    public Post post;

    public String url;

    public int sum = 0;

    public Result(Post post,String url){
        this.post = post;
        this.url = url;
    }

    public Result(Post post,int sum){
        this.post = post;
        this.sum = sum;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
