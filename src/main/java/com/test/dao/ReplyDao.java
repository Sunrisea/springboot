package com.test.dao;

import com.test.model.Reply;

import java.util.List;

public interface ReplyDao {
    void addReply(Reply reply);
    Reply getReplyByRid(int rid);
    List<Reply> getReplyByPid(int pid);//同一帖子的所有评论
    List<Reply> getAllReply();
    void removeReply(int rid);
    void removeReplyByPid(int pid);
    int getReplyNumberByPid(int pid);
    boolean replyAddLikes(String username,int pid,int rid);//增加帖子点赞数
}
