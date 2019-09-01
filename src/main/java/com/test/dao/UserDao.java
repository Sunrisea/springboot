package com.test.dao;

import com.test.model.User;

import java.util.List;

public interface UserDao {
    void addUser(String username, String password);
    User getUserByUsername(String username);
    void removeUserByUsername(String username);
    void removeAllUsers();
    List<User>getAllUsers();
    boolean isExist(String username);
    User getUserByUsernameAndPassword(String username, String password);

    User changeUserData(String username,User newUser);
    User changeUserCode(String username,User newUser);

    User banUser(String username);
    User freeUser(String username);

}
