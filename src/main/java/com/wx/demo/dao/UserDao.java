package com.wx.demo.dao;

import com.wx.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    void save(User user);

    void update(User user);

    void remove(Long userId);

    User getById(Long userId);

    User getByName(String userName);

    List<User> listAll();

    List<User> listByPage(Map<String, Object> map);
}
