package com.supermarket.service.impl;

import com.supermarket.pojo.User;
import com.supermarket.dao.LoginDao;
import com.supermarket.service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class loginServiceImpl implements loginService {

    @Autowired
    private LoginDao accountDao;

    //登录
    @Override
    public User getUser(String username, String password,String role) {
        return accountDao.getUser(username, password,role);
    }



}
