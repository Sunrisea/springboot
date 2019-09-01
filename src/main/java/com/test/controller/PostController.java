package com.test.controller;


import com.test.model.Post;
import com.test.model.Result;
import com.test.model.UploadFile;
import com.test.service.PostService;
import com.test.service.ReplyService;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    ReplyService replyService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/publish")
    public Post publishPost(@RequestBody Post requestPost){

        if(requestPost.getZone().equals("新手上路"))
            postService.publishGreenHands(requestPost);
        else
            postService.publishPost(requestPost);
        return requestPost;
    }

    @CrossOrigin
    @PostMapping(value = "/remove")
    public void removePost(@RequestParam int pid){
        postService.removePost(pid);
        replyService.removeReplyByPid(pid);
    }


    //主页分板块帖子展示
    @CrossOrigin
    @PostMapping(value = "/mainpage")
    public List<Result> mainpageZonePost(@RequestParam(value = "zone") String zone){

        List<Post> posts = postService.topReplyPost(zone);

        List<Result> results = new ArrayList<Result>();

        for(Post post:posts){
            String username = post.getAuthorname();
            String url;
            UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
            if(file == null)
                url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
            else
                url = "http://114.116.4.94:8666/api/image/"+ file.getId();

            Result newresult = new Result(post,url);
            results.add(newresult);
//            System.out.println(newresult.post.toString());
//            System.out.println(newresult.url);
        }
//        System.out.println(results);
        return results;
    }

    //板块页帖子展示
    @CrossOrigin
    @PostMapping(value = "/zonepage")
    public List<Result> zonePagePost(@RequestParam(value = "zone") String zone,
                                     @RequestParam(value = "page") int page){
//        System.out.println("zone:"+zone+"page:"+page);
        List<Post> posts = postService.newestPost(zone,page);
        List<Result> results = new ArrayList<Result>();
        int sum = postService.getPostNumberByZone(zone);
        for(Post post:posts){
            String username = post.getAuthorname();
            String url;
            UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
            if(file == null)
                url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
            else
                url = "http://114.116.4.94:8666/api/image/"+ file.getId();
            Result newresult = new Result(post,url);
            newresult.setSum(sum);
//            System.out.println("newresult:"+newresult);
            results.add(newresult);
        }
//        System.out.println(results);
        return results;
    }

    @CrossOrigin
    @PostMapping(value = "/like")
    public boolean pidBeLiked(@RequestParam(value = "pid") int pid){
        Post post = postService.findPost(pid);
//        post.addLike();
        return true;
    }
}
