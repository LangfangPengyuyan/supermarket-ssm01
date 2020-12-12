package com.supermarket.service;


import com.supermarket.pojo.User;

public interface loginService {

    //登录
    public User getUser(String username, String password,String role);


}
