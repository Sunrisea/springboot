package com.test.service;

import com.test.model.Post;

import java.util.List;

public interface PostService {
    boolean publishPost(Post post);//发帖
    boolean removePost(int pid);//删帖
    boolean isLiked(String username,int pid);//点赞
    List<Post> topReplyPost(String zone);//zone板块中回复量最高的前五个帖
    List<Post> newestPost(String zone,int i);//zone板块中发布时间排序实现分页
    Post findPost(int pid);//根据pid查找Post
    int getPostNumberByZone(String zone);//获取板块内帖子总数
    List<Post> getPostByUsername(String username,int i);//获取username用户所有帖子实现分页
    int getPostNumberByUsername(String username);//获取username用户发布的帖子总数
    void publishGreenHands(Post requestPost);//发布新手上路
    Post getPostByPid(int pid);//通过pid获取帖子

}
