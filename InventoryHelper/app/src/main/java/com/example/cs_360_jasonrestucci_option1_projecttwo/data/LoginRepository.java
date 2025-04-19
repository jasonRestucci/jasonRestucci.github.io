package com.example.cs_360_jasonrestucci_option1_projecttwo.data;

import com.example.cs_360_jasonrestucci_option1_projecttwo.data.model.LoggedInUser;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Class that requests authentication and user information from the remote data source
 * and maintains an in-memory cache of login status and user credentials information.
 */

public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }


    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}