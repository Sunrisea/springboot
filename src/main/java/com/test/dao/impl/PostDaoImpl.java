package com.test.dao.impl;

import com.test.dao.PostDao;
import com.test.model.Like;
import com.test.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostDaoImpl implements PostDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void addPost(Post post) {
//        System.out.println("addPost");
        mongoTemplate.insert(post);
    }

    @Override
    public Post getPostByPid(int pid) {
        Post post = mongoTemplate.findOne(new Query(Criteria.where("pid").is(pid)),Post.class);
        return post;
    }

    @Override
    public List<Post> getPostByZone(String zone) {
//        System.out.println("getPostByZone:"+zone);
        List<Post> posts = mongoTemplate.find(new Query(Criteria.where("zone").is(zone)),Post.class);
        return posts;
    }

    @Override
    public List<Post> getPostByUsername(String username) {
        return mongoTemplate.find(new Query(Criteria.where("authorname").is(username)),Post.class);
    }

    @Override
    public void removePostByPid(int pid) {
        mongoTemplate.remove(new Query(Criteria.where("pid").is(pid)),Post.class);
    }

    @Override
    public void removeAllPosts() {
        mongoTemplate.dropCollection(Post.class);
    }

    @Override
    public List<Post> getAllPosts() {
        return mongoTemplate.findAll(Post.class);
    }

    @Override
    public void postAddRids(Post post) {
        Query query = new Query();
        query.addCriteria(Criteria.where("pid").is(post.getPid()));
        Update update = Update.update("rids",post.getRids()+1);
        mongoTemplate.upsert(query, update, Post.class);
    }

    @Override
    public boolean postAddLikes(String username,int pid) {

        System.out.println("postdao pid "+pid);

        List<Like> haveliked = mongoTemplate.find(new Query(Criteria.where("username").is(username)),Like.class);
        Like getlike = null;

        for(Like like:haveliked){
            if(like.getPid() == pid)
                getlike = like;
        }

        if(getlike != null)
            return false;


        Post post = mongoTemplate.findOne(new Query(Criteria.where("pid").is(pid)),Post.class);
        if(post == null)
            return false;

        System.out.println("update");
        //update 表post
        Query query = new Query();
        query.addCriteria(Criteria.where("pid").is(pid));
        Update update = Update.update("like",post.getLike()+1);
        mongoTemplate.upsert(query, update, Post.class);

        //insert 表like
        Like like = new Like();
        like.setRid(0);
        like.setPid(pid);
        like.setUsername(username);
        mongoTemplate.insert(like);
        return true;
    }
}
