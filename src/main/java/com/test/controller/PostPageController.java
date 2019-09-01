package com.test.controller;

import com.test.model.Post;
import com.test.model.Result;
import com.test.model.UploadFile;
import com.test.service.PostService;
import com.test.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postpage")
public class PostPageController {

    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @CrossOrigin
    @PostMapping(value = "/open")
    public Result postPageOpen(@RequestParam(value = "pid") int pid){
        Post post = postService.getPostByPid(pid);
        String username = post.getAuthorname();
        UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
        String url;
        if(file == null)
            url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
        else
            url = "http://114.116.4.94:8666/api/image/"+ file.getId();
        Result result = new Result(post,url);
        return result;
    }

    @CrossOrigin
    @PostMapping(value = "/postlike")
    public boolean postIsLiked(@RequestParam(value = "username") String username,
                               @RequestParam(value = "pid") int pid){
        System.out.println("postpagecontroller pid"+pid);
        return postService.isLiked(username,pid);
    }

    @CrossOrigin
    @PostMapping(value = "/replylike")
    public boolean replyIsLiked(@RequestParam(value = "username") String username,
                                @RequestParam(value = "pid") int pid,
                                @RequestParam(value = "rid") int rid){
        return replyService.isLiked(username,pid,rid);
    }

}
