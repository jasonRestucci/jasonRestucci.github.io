package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

import androidx.annotation.Nullable;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Authentication result : success (user details) or error message.
 */

class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}