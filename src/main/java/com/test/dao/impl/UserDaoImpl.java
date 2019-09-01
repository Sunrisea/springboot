package com.test.dao.impl;


import com.test.dao.UserDao;
import com.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void addUser(String username,String password) {

        User user = new User(username,password);
        mongoTemplate.insert(user);
    }

    @Override
    public void removeUserByUsername(String username) {
        mongoTemplate.remove(new Query(Criteria.where("username").is(username)));
    }

    @Override
    public void removeAllUsers() {
        mongoTemplate.dropCollection(User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public boolean isExist(String username){
        User user = getUserByUsername(username);
        return null!=user;
    }
    @Override
    public User getUserByUsername(String username) {
//        User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),User.class);
        return mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),User.class);
//        System.out.println(user.getBirthday());
//        return user;
    }
    @Override
    public User getUserByUsernameAndPassword(String username,String password){
        User user = getUserByUsername(username);
        if(getUserByUsername(username).getPassword().equals(password))
            return user;
        else
            return null;
    }

    @Override
    public User changeUserData(String username,User newUser){
//        User oldUser = getUserByUsername(username);
        User oldUser = getUserByUsername(username);

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
//        update.set("password",newUser.getPassword());
        update.set("sex",newUser.getSex());
        update.set("birthday",newUser.getBirthday());
        update.set("region",newUser.getRegion());
//        update.set("state",newUser.getState());
        update.set("motto",newUser.getMotto());
        mongoTemplate.upsert(query, update, "user");

        User changedUser = getUserByUsername(username);
        return changedUser;
    }

    @Override
    public  User changeUserCode(String username,User newUser){
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("password",newUser.getPassword());
        mongoTemplate.upsert(query, update, "user");
        User changedUser = getUserByUsername(username);
        return changedUser;
    }

    @Override
    public User banUser(String username){
        User user = getUserByUsername(username);
        if (user == null){
            return null;
        }
        else {
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));
            Update update = new Update();
            update.set("state","1");
            mongoTemplate.upsert(query, update, "user");
            user.setState("1");
            return user;
        }
    }

    @Override
    public User freeUser(String username){
        User user = getUserByUsername(username);
        if (user == null){
            return null;
        }
        else {
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));
            Update update = new Update();
            update.set("state","0");
            mongoTemplate.upsert(query, update, "user");
            user.setState("0");
            return user;
        }
    }

}
