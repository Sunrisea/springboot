package com.test.service.impl;

import com.test.dao.PostDao;
import com.test.model.Post;
import com.test.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public boolean publishPost(Post newpost) {
        List<Post> posts = postDao.getAllPosts();

        int i=1;
        for(Post post:posts) {
            if(post.getPid()>i)
                i=post.getPid();
        }
        newpost.setPid(i+1);
        postDao.addPost(newpost);
        return true;
    }

    @Override
    public void publishGreenHands(Post newPost) {
        Post post = postDao.getPostByPid(0);
        if(post != null)
            postDao.removePostByPid(0);
        newPost.setPid(0);
        postDao.addPost(newPost);
    }

    @Override
    public Post getPostByPid(int pid) {
        return postDao.getPostByPid(pid);
    }

    @Override
    public boolean removePost(int pid) {
        postDao.removePostByPid(pid);
        return true;
    }

    @Override
    public boolean isLiked(String username,int pid) {
        System.out.println("postservice pid"+pid);
        return postDao.postAddLikes(username,pid);
    }

    @Override
    public List<Post> topReplyPost(String zone) {
        List<Post> posts = postDao.getPostByZone(zone);

        //按回复数降序排序
        Comparator<Post> postComparator = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return o2.getRids()-o1.getRids();
            }
        };
        posts.sort(postComparator);

        int fromIndex = 0;
        int toIndex = 5>posts.size()?posts.size():5;

        return posts.subList(fromIndex,toIndex);
    }

    @Override
    public List<Post> newestPost(String zone,int i) {
        List<Post> posts = postDao.getPostByZone(zone);

        //按发帖时间降序排序
        Comparator<Post> postComparator = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                //以秒为单位
                return (int)((o2.getTime().getTime()-o1.getTime().getTime())/1000);
            }
        };

        posts.sort(postComparator);

        //[0,10)表示排0-9的帖子
        int fromindex = (i-1)*10;
        int toindex = i*10>posts.size() ? posts.size() : i*10;

        return posts.subList(fromindex,toindex);
    }

    @Override
    public Post findPost(int pid) {
        return postDao.getPostByPid(pid);
    }

    @Override
    public int getPostNumberByZone(String zone) {
        return postDao.getPostByZone(zone).size();
    }

    @Override
    public List<Post> getPostByUsername(String username,int i) {
//        System.out.println("username"+username);
        List<Post> posts = postDao.getPostByUsername(username);

        System.out.println("num"+posts.size());
        //按发帖时间降序排序
        Comparator<Post> postComparator = new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                //以秒为单位
                return (int)((o2.getTime().getTime()-o1.getTime().getTime())/1000);
            }
        };

        posts.sort(postComparator);

        //[0,10)表示排0-9的帖子
        int fromindex = (i-1)*10;
        int toindex = i*10>posts.size() ? posts.size() : i*10;

//        System.out.println("index:"+fromindex+"-"+toindex);
        return posts.subList(fromindex,toindex);
    }

    @Override
    public int getPostNumberByUsername(String username) {

        return postDao.getPostByUsername(username).size();
    }

}
