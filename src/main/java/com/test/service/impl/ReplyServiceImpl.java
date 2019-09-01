package com.test.service.impl;

import com.test.dao.PostDao;
import com.test.dao.ReplyDao;
import com.test.model.Post;
import com.test.model.Reply;
import com.test.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ReplyDao replyDao;

    @Autowired
    PostDao postDao;

    //发布评论
    @Override
    public boolean publishReply(Reply newreply) {
//        System.out.println("publishReply\n");
        int pid = newreply.getPid();
        List<Reply> replys = replyDao.getReplyByPid(pid);
        Post post = postDao.getPostByPid(pid);
        postDao.postAddRids(post);
        System.out.println(post.toString());
        int i=1;
        for(Reply reply:replys){
            if(reply.getRid()>i)
                i = reply.getRid();
        }
        newreply.setRid(i+1);
        replyDao.addReply(newreply);
        return true;
    }

    //删除评论
    @Override
    public boolean removeReply(int rid) {
        replyDao.removeReply(rid);
        return true;
    }

    @Override
    public boolean removeReplyByPid(int pid) {
        replyDao.removeReplyByPid(pid);
        return true;
    }

    @Override
    public boolean isLiked(String username, int pid, int rid) {
        return replyDao.replyAddLikes(username,pid,rid);
    }

    //评论按发布时间排序，每页10条评论
    @Override
    public List<Reply> newReply(int pid, int page) {
        List<Reply> replies = replyDao.getReplyByPid(pid);

        Comparator<Reply> replyComparator = new Comparator<Reply>() {
            @Override
            public int compare(Reply o1, Reply o2) {
                return (int)((o1.getTime().getTime()-o2.getTime().getTime())/1000);
            }
        };
        replies.sort(replyComparator);

        int fromindex = (page-1)*10;
        int toindex = page*10>replies.size() ? replies.size() : page*10;

        return replies.subList(fromindex,toindex);
    }

    //根据rid搜索评论
    @Override
    public Reply findReply(int rid) {
        return replyDao.getReplyByRid(rid);
    }

    //同一篇帖子的评论数目
    @Override
    public int getReplyNumByPid(int pid) {
        return replyDao.getReplyNumberByPid(pid);
    }

    @Override
    public List<Reply> getReplyByPid(int pid) {
        return replyDao.getReplyByPid(pid);
    }
}
