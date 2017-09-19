package com.wx.demo.service;

import com.wx.demo.base.entity.PageBean;
import com.wx.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 新增一个用户
     * @param user
     */
    void save(User user);

    /**
     * 更新一个用户
     */
    void update(User user);

    /**
     * 删除一个用户
     * @param userId
     */
    void remove(Long userId);

    /**
     * 获取一个用户
     */
    User getById(Long userId);

    /**
     * 获取一个用户
     */
    User getByName(String userName);

    /**
     * 获取用户列表
     */
    List<User> listAll();

    /**
     * 获取用户列表分页
     */
    PageBean<User> listByPage(Map<String, Object> map);

}
