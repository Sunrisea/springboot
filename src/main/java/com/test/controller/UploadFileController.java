package com.test.controller;

import com.test.service.UserService;
import org.bson.types.Binary;
import com.test.model.UploadFile;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UploadFileController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    UserService userService;

    private Map<String,Object> params = new HashMap<String,Object>();

    @CrossOrigin
    @PostMapping("/test/uploadImage")
    public Object uploadImage(@RequestParam(value = "image") MultipartFile file,
                              @RequestParam(value = "username") String username){
        if(file.isEmpty()) {
            params.clear();
            params.put("result", "noImageUpload");
            return params;
        }
        String fileName = file.getOriginalFilename();
        try {
            UploadFile oldFile = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
            if(oldFile!=null)
                mongoTemplate.remove(oldFile);
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(fileName);
            uploadFile.setUsername(username);
            uploadFile.setCreatedTime(new Date());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());
            UploadFile savedFile = mongoTemplate.save(uploadFile);
            String url = "http://114.116.4.94:8666/api/image/"+ savedFile.getId();//访问地址
            System.out.println("url"+url);
            params.clear();
            System.out.println(url);
            params.put("result", "success");
            params.put("url",url);
        } catch (IOException e) {
            e.printStackTrace();
            params.clear();
            params.put("result", "uploadFail");
        }
        return params;
    }

    @CrossOrigin
    @PostMapping("/test/userimage")
    public String userImage(@RequestParam(value = "username") String username){
		String url = null;
        UploadFile file = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),UploadFile.class);
        if(file == null)
          	url = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
      	else
          	url = "http://114.116.4.94:8666/api/image/"+file.getId();
      	return url;
    }

    @CrossOrigin
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] image(@PathVariable String id){
        System.out.println("id"+id);
        byte[] data = null;
        UploadFile file = mongoTemplate.findById(id, UploadFile.class);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }
}
