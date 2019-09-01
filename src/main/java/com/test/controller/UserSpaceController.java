package com.test.controller;

import com.test.model.Post;
import com.test.model.Result;
import com.test.model.UploadFile;
import com.test.service.PostService;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/userspace")
public class UserSpaceController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;

    @CrossOrigin
    @PostMapping("/myposts")
    public List<Result> myposts(@RequestParam(value = "username") String username,
                                @RequestParam(value = "page") int page){
//        System.out.println("aaaa"+username);
        UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
        String url = null;
        if(file == null)
            url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
        else
            url = "http://114.116.4.94:8666/api/image/"+ file.getId();

        List<Post> posts = postService.getPostByUsername(username,page);
        List<Result> results = new ArrayList<Result>();

        int sum = postService.getPostNumberByUsername(username);
        for(Post post:posts){
            Result newresult = new Result(post,sum);
            newresult.setUrl(url);
            results.add(newresult);
        }
//        System.out.println(sum);
        return results;

    }



}
