package com.wx.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.wx.demo.base.entity.PageBean;
import com.wx.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wx.demo.dao.UserDao;
import com.wx.demo.service.UserService;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void remove(Long userId) {
        userDao.remove(userId);
    }

    @Override
    public User getById(Long userId) {
        return userDao.getById(userId);
    }

    @Override
    public User getByName(String userName) {
        return userDao.getByName(userName);
    }

    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Override
    public PageBean<User> listByPage(Map<String, Object> map){
        int pageNum = (Integer) map.get("pageNum");
        int pageSize = (Integer) map.get("pageSize");

        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userDao.listByPage(map);

        PageBean<User> page = new PageBean<>(list);

        return page;
    }

}
