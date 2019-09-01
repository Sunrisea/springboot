package com.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "post")
public class Post {

    @Id
    private String id;

    //帖子ID号
    @Field("pid")
    private int pid;

    //帖子标题
    @Field("title")
    private String title;

    //发帖用户名
    @Field("authorname")
    private String authorname;

    //所属板块
    @Field("zone")
    private String zone;

    //发帖时间
    @Field("time")
    private Date time;

    //发帖内容
    @Field("content")
    private String content;

    //点赞数
    @Field("like")
    private int like = 0;

    //该帖回复数
    @Field("rids")
    private int rids = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getRids() {
        return rids;
    }

    public void setRids(int rids) {
        this.rids = rids;
    }

    @Override
    public String toString() {
        return "Pid:"+getPid()+"\n"+
                "Title:"+getTitle()+"\n"+
                "Author:"+getAuthorname()+"\n"+
                "Zone:"+getZone()+"\n"+
                "Time:"+getTime()+"\n"+
                "Content:"+getContent()+"\n"+
                "Like:"+getLike()+"\n"+
                "Rids:"+getRids()+"\n";
    }
}
