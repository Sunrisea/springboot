package com.test.controller;

import com.test.model.Reply;
import com.test.model.Result2;
import com.test.model.UploadFile;
import com.test.service.ReplyService;
import com.test.service.UserService;
//import org.graalvm.compiler.lir.alloc.lsra.LinearScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    ReplyService replyService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/publish")
    public Reply publishReply(@RequestBody Reply requestReply){
        System.out.println(requestReply);
        replyService.publishReply(requestReply);
        return requestReply;
    }

//    CrossOrigin
//    @PostMapping(value = "/")

    @CrossOrigin
    @PostMapping(value = "/postpage")
    public List<Result2> postReply(@RequestParam(value = "pid") int pid,
                                   @RequestParam(value = "page")  int page){
        System.out.println("pid:"+pid+"page:"+page);

        List<Reply> replies = replyService.newReply(pid,page);
        List<Result2> result2s = new ArrayList<Result2>();
        int sum = replyService.getReplyNumByPid(pid);
        for (Reply reply:replies){
            String username = reply.getReplyname();
            String url;

            UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
            if (file==null)
                url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
            else
                url = "http://114.116.4.94:8666/api/image/"+ file.getId();
            Result2 newResult = new Result2(reply,url);
            newResult.setSum(sum);
            result2s.add(newResult);
        }
        return result2s;
    }
}













