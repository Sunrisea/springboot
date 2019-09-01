package com.test.service;

import com.test.model.Post;
import com.test.model.Reply;

import java.util.List;

public interface ReplyService {
    boolean publishReply(Reply reply);//发评论
    boolean removeReply(int rid);//删评论
    boolean removeReplyByPid(int pid);
    boolean isLiked(String username,int pid,int rid);//点赞

    List<Reply> newReply(int pid,int page);//同一帖子中发布时间排序实现分页
    Reply findReply(int rid);//根据rid找评论
    int getReplyNumByPid(int pid);

    List<Reply> getReplyByPid(int pid);
}
