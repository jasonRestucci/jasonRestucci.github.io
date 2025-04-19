package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

public class User {
    private int userId;
    private String userName;
    private String userPass;

    /**
     * Author: Jason Restucci
     * Date Last Modified: 3/18/25
     * Description: Object class for users
     */
    public User(int userId, String userName, String userPass) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserpass() {
        return userPass;
    }

    public void setUserpass(String userPass) {
        this.userPass = userPass;
    }
}
