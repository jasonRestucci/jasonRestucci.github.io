package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.registerMode;
import static com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginActivity.userArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs_360_jasonrestucci_option1_projecttwo.data.LoginRepository;
import com.example.cs_360_jasonrestucci_option1_projecttwo.data.Result;
import com.example.cs_360_jasonrestucci_option1_projecttwo.data.model.LoggedInUser;
import com.example.cs_360_jasonrestucci_option1_projecttwo.R;

import java.util.ArrayList;

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



    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else{
            return true;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        else{
            return true;
        }
    }
}