package com.test.dao;

import com.test.model.Post;

import java.util.List;

public interface PostDao {
    void addPost(Post post);//发帖
    Post getPostByPid(int pid);//通过pid获取帖子
    List<Post> getPostByZone(String zone);//获取zone板块中的帖子
    List<Post> getPostByUsername(String username);//获取username用户发布的帖子
    void removePostByPid(int pid);//通过pid删除帖子
    void removeAllPosts();//删除所有帖子
    List<Post> getAllPosts();//获取所有帖子
    void postAddRids(Post post);//增加帖子回复数
    boolean postAddLikes(String username,int pid);//增加帖子点赞数
}
