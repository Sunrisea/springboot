package com.test.service;

import com.test.model.User;

public interface UserService {
    public int login(String username, String password);
    public int signup(String username, String password, String repassword);
    public User changeData(String username, User newUser);
    public User changeCode(String username, User newUser);
    public User getData(String username);

    public int banUser(String username);
    public int freeUser(String username);
}
