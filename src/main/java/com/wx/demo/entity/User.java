package com.wx.demo.entity;

public class User {
    private Long userId;
    private String userName;
    private String userPassword;
    private Integer userStatus;
    private Integer userLocked;
    private Integer age;
    private Integer createBy;
    private Integer updateBy;
    private String createTime;
    private String updateTime;

    public User() {
        super();
    }

    public User(User user) {
        this.userId       = user.getUserId();
        this.userName     = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.userStatus   = user.getUserStatus();
        this.userLocked   = user.getUserLocked();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserLocked() {
        return userLocked;
    }

    public void setUserLocked(Integer userLocked) {
        this.userLocked = userLocked;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
