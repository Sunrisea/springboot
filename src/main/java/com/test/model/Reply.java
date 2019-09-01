package com.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "reply")
public class Reply {

    @Id
    private String id;

    //评论的id号
    @Field("rid")
    private int rid;

    //评论的帖子id
    @Field("pid")
    private int pid;

    //回复者用户名
    @Field("replyname")
    private String replyname;

    //回复内容
    @Field("content")
    private String content;

    //回复时间
    @Field("time")
    private Date time;

    //点赞数
    @Field("like")
    private int like = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getReplyname() {
        return replyname;
    }

    public void setReplyname(String replyname) {
        this.replyname = replyname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString(){
        return "Rid:"+getRid()+"\n"+
                "Pid:"+getPid()+"\n"+
                "Replyname:"+getReplyname()+"\n"+
                "Content:"+getContent()+"\n"+
                "Time:"+getTime()+"\n"+
                "Like:"+getLike()+"\n";
    }

}
