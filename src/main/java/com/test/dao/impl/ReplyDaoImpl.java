package com.test.dao.impl;

import com.test.dao.ReplyDao;
import com.test.model.Like;
import com.test.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
//import sun.jvm.hotspot.debugger.MachineDescriptionIntelX86;

import java.util.List;
@Component
public class ReplyDaoImpl implements ReplyDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    //发布评论
    @Override
    public void addReply(Reply reply) {
        System.out.println("addReply");
        mongoTemplate.insert(reply);
    }

    //根据rid搜索评论
    @Override
    public Reply getReplyByRid(int rid) {
        Reply reply = mongoTemplate.findOne(new Query(Criteria.where("rid").is(rid)),Reply.class);

        return reply;
    }

    //同一篇帖子的所有评论
    @Override
    public List<Reply> getReplyByPid(int pid) {
        System.out.println("getReplyByPid:"+pid);
        List<Reply> replies = mongoTemplate.find(new Query(Criteria.where("pid").is(pid)),Reply.class);
        return replies;
    }


    //获取所有评论
    @Override
    public List<Reply> getAllReply() {
        return mongoTemplate.findAll(Reply.class);
    }

    //根据rid删除评论
    @Override
    public void removeReply(int rid) {
        mongoTemplate.remove(new Query(Criteria.where("rid").is(rid)),Reply.class);
    }

    @Override
    public void removeReplyByPid(int pid) {
        mongoTemplate.remove(new Query(Criteria.where("pid").is(pid)),Reply.class);
    }

    //一篇帖子的评论数目
    @Override
    public int getReplyNumberByPid(int pid) {
        List<Reply> replies = mongoTemplate.find(new Query(Criteria.where("pid").is(pid)),Reply.class);
        return replies.size();
    }

    @Override
    public boolean replyAddLikes(String username, int pid, int rid) {

        List<Like> haveliked = mongoTemplate.find(new Query(Criteria.where("username").is(username)),Like.class);
        Like getlike = null;

        for(Like like:haveliked){
            if(like.getPid() == pid && like.getRid() == rid)
                getlike = like;
        }
        if(getlike != null)
            return false;

        List<Reply> reply = mongoTemplate.find(new Query(Criteria.where("pid").is(pid)),Reply.class);
        Reply getreply = null;
        for(Reply reply1:reply){
            if(reply1.getRid() == rid)
                getreply = reply1;
        }
        if(getreply == null)
            return false;


        //update 表reply
        Query query = new Query();
        query.addCriteria(Criteria.where("pid").is(pid).and("rid").is(rid));
        Update update = Update.update("like",getreply.getLike()+1);
        mongoTemplate.upsert(query, update, Reply.class);

        //insert 表like
        Like like = new Like();
        like.setRid(rid);
        like.setPid(pid);
        like.setUsername(username);
        mongoTemplate.insert(like);
        return true;
    }
}
