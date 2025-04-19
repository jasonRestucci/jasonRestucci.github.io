package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.registerMode;
import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.userArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs_360_jasonrestucci_option1_projecttwo.data.LoginRepository;
import com.example.cs_360_jasonrestucci_option1_projecttwo.data.Result;
import com.example.cs_360_jasonrestucci_option1_projecttwo.data.model.LoggedInUser;
import com.example.cs_360_jasonrestucci_option1_projecttwo.R;

import java.util.ArrayList;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/18/25
 * Description: Model for login
 */

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        boolean isFound = false;
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }



    // username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else{
            for (int i = 0; i < userArrayList.size(); i++) {
                if(username.equals(userArrayList.get(i).getUsername())){
                    return true;
                }
            }
        }
        return false;
    }

    // password validation check
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        else{
            for (int i = 0; i < userArrayList.size(); i++) {
                if(password.equals(userArrayList.get(i).getUserpass())){
                    return true;
                }
            }
        }
        return false;
    }
}