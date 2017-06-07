package com.sawallianc.service.impl;

import com.sawallianc.dao.UserDAO;
import com.sawallianc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fingertap on 2017/6/7.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO dao;
    @Override
    public void update() {
        dao.update();
    }
}
