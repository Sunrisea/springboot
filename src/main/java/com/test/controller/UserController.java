package com.test.controller;


import com.test.model.Result3;
import com.test.model.User;
import com.test.model.Result;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/login")
    public Result3 login(@RequestBody User requestUser){
        int result = userService.login(requestUser.getUsername(),requestUser.getPassword());
        //登录失败，返回400；登陆成功，是普通用户，返回200；是管理员，返回500；是封禁用户，返回300；
        return new Result3(result);
    }

    @CrossOrigin
    @PostMapping(value = "/signup")
    public Result3 signup(@RequestBody User requestUser){
        int result = userService.signup(requestUser.getUsername(),requestUser.getPassword(),requestUser.getRepassword());
        //用户名已存在，返回300；密码不一致，返回400；用户名长度不符合规范（长度应为1-6），返回500；密码长度不符合规范（长度应为6-18），返回600
        return new Result3(result);
    }

    @CrossOrigin
    @PostMapping(value = "/userzone")
    public User changeUserData(@RequestBody User requestUser){
        User user = userService.changeData(requestUser.getUsername(),requestUser);
//        System.out.println("name: "+ requestUser.getUsername());
        return user;
    }

    @CrossOrigin
    @PostMapping(value = "/usercode")
    public User changeUserCode(@RequestBody User requestUser){
        User user = userService.changeCode(requestUser.getUsername(),requestUser);
//        System.out.println("name: "+ requestUser.getUsername());
        return user;
    }

    @CrossOrigin
    @PostMapping(value = "/userdata")
    public User gerUserData(@RequestBody User requestUser){
        User user = userService.getData(requestUser.getUsername());
        return user;
    }

    @CrossOrigin
    @PostMapping(value = "/banuser")
    public Result3 banUser(@RequestBody User requestUser){
        int result = userService.banUser(requestUser.getUsername());
        //返回值500，查无此人；返回400，是管理员；返回300，已经封禁；返回200，成功禁言
        return new Result3(result);
    }

    @CrossOrigin
    @PostMapping(value = "/freeuser")
    public Result3 freeUser(@RequestBody User requestUser){
        int result = userService.freeUser(requestUser.getUsername());
        //返回值500，查无此人；返回400，是管理员；返回300，普通用户；返回200，成功解封
        return new Result3(result);
    }

}
