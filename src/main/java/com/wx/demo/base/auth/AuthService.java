package com.wx.demo.base.auth;

import com.wx.demo.entity.User;

/**
 * Created by wangxiong on 2017/9/16.
 */
public interface AuthService {
    User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}