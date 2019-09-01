package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.model.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    //账号登录
    @Override
    public int login(String username, String password) {
        //登录失败，返回400；登陆成功，是普通用户，返回200；是管理员，返回500；是封禁用户，返回300；
        try {
            User user = userDao.getUserByUsernameAndPassword(username, password);
            if(user == null){
                return 400;
            }
            else if(user.getState().equals("2")){
                return 500;
            }
            else if(user.getState().equals("1")){
                return 300;
            }
            else{
                return 200;
            }
        } catch (Exception e) {
//            System.out.println("失败" + e.getMessage());
            return 400;
        }
    }


    //账号注册
    @Override
    public int signup(String username,String password,String repassword){
        if(username.length() > 6 || username.length() == 0){
            return 500;
        }
        else if(userDao.isExist(username)){
//            System.out.println("username: "+ username);
            return 300;
        }
        else if(password.length() < 6 || password.length() > 18 )
            return 600;
        else if(!password.equals(repassword))
            return 400;
        else {
            userDao.addUser(username,password);
//            System.out.println("username: "+ username);
            return 200;
        }
    }

    @Override
    public User changeData(String username, User newUser){
        User user = userDao.changeUserData(username,newUser);
        return user;
    }

    @Override
    public User changeCode(String username, User newUser){
        User user = userDao.changeUserCode(username,newUser);
        return user;
    }

    @Override
    public User getData(String username){
        User user = userDao.getUserByUsername(username);
        return user;
    }

    @Override
    public int banUser(String username){
        User user = userDao.getUserByUsername(username);
//        User user =userDao.banUser(username);
        if (user == null){
            //查无此人
            return 500;
        }
        else if (user.getState().equals("2")){
            //是管理员
            return 400;
        }
        else if (user.getState().equals("1")){
            //已经被封
            return 300;
        }
        else {
            userDao.banUser(username);
            return 200;
        }
    }

    @Override
    public int freeUser(String username){
        User user = userDao.getUserByUsername(username);
        if (user == null){
            //查无此人
            return 500;
        }
        else if (user.getState().equals("2")){
            //是管理员
            return 400;
        }
        else if (user.getState().equals("0")){
            //普通用户
            return 300;
        }
        else {
            userDao.freeUser(username);
            return 200;
        }
    }
}
