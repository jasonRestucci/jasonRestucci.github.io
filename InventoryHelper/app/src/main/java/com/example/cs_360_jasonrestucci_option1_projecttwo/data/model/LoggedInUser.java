package com.example.cs_360_jasonrestucci_option1_projecttwo.data.model;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Data class that captures user information for logged in users retrieved from LoginRepository
 */

public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}