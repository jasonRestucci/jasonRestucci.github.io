package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

/**
 * Author: Jason Retucci
 * Date Last Modified: 3/18/25
 * Description: Class exposing authenticated user details to the UI.
 */

class LoggedInUserView {
    private String displayName;

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}