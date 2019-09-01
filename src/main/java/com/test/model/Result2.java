package com.test.model;

public class Result2 {

    public Reply reply;

    public String url;

    public int sum;

    public Result2(Reply reply,String url){
        this.reply = reply;
        this.url = url;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
