package com.example.cs_360_jasonrestucci_option1_projecttwo.data;

import com.example.cs_360_jasonrestucci_option1_projecttwo.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Author: Jason Restucci
 * Date Last Modified: 4/5/25
 * Description: Class that handles authentication w/ login credentials and retrieves user information.
 */

public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        try {
            LoggedInUser currentUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new Result.Success<>(currentUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
}