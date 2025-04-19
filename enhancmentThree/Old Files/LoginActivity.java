package com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs_360_jasonrestucci_option1_projecttwo.MainActivity;
import com.example.cs_360_jasonrestucci_option1_projecttwo.NewItemMenu;
import com.example.cs_360_jasonrestucci_option1_projecttwo.R;
import com.example.cs_360_jasonrestucci_option1_projecttwo.SQLiteManager;
import com.example.cs_360_jasonrestucci_option1_projecttwo.SQLiteManagerUser;
import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginViewModel;
import com.example.cs_360_jasonrestucci_option1_projecttwo.ui.login.LoginViewModelFactory;
import com.example.cs_360_jasonrestucci_option1_projecttwo.databinding.ActivityLoginBinding;

import java.util.ArrayList;

/**
 * Author: Jason Restucci
 * Date Last Modified: 3/29/25
 * Description: Main file for login screen, handles button and UI functionality
 */

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    public static ArrayList<User> userArrayList = new ArrayList<User>();
    public static boolean registerMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        // link variables to UI elements
        final EditText usernameEditText = binding.editTextUsername;
        final EditText passwordEditText = binding.editTextPassword;
        final Button loginButton = binding.buttonSignin;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button registerButton = binding.buttonRegister;
        final EditText registerEditTextUsername = binding.editTextRegisterUsername;
        final EditText registerEditTextPassword = binding.editTextRegisterPassword;
        final Button registerButtonNew = binding.buttonregisterNew;

        //populate ArrayLists with database items
        loadFromDBToMemory();

        //Enables login button with successful credentials, produces errors
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!registerMode) {
                    loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                else{
                    loginViewModel.loginDataChanged(registerEditTextUsername.getText().toString(),
                            registerEditTextPassword.getText().toString());

                }
            }
        };
            usernameEditText.addTextChangedListener(afterTextChangedListener);
            passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        loginViewModel.login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }
                return false;
            }
        });

        // searches for matching user and logs them in
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFound = false;
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                for (int i = 0; i < userArrayList.size(); i++) {
                    if (username.equals(userArrayList.get(i).getUsername()) && password.equals(userArrayList.get(i).getUserpass())) {
                        isFound = true;
                    }
                }
                if  (isFound){
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(), "username/password not found, new users must register", Toast.LENGTH_LONG).show();
                }
            }
        });

        //switches login and register mode
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!registerMode) {
                    registerEditTextUsername.setVisibility(View.VISIBLE);
                    registerEditTextPassword.setVisibility(View.VISIBLE);
                    registerButtonNew.setVisibility(View.VISIBLE);
                    usernameEditText.setVisibility(View.GONE);
                    passwordEditText.setVisibility(View.GONE);
                    loginButton.setVisibility(View.GONE);
                    registerMode = true;
                    registerButton.setText("Back To Login");
                }
                else{
                    registerEditTextUsername.setVisibility(View.GONE);
                    registerEditTextPassword.setVisibility(View.GONE);
                    registerButtonNew.setVisibility(View.GONE);
                    usernameEditText.setVisibility(View.VISIBLE);
                    passwordEditText.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    registerMode = false;
                    registerButton.setText("Register");
                }
            }
        });

        // registers a new user with user input
        registerButtonNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try (SQLiteManagerUser sqLiteManagerUser = SQLiteManagerUser.instanceOfUserDatabase(LoginActivity.this)) {
                    boolean isFound = false;
                    String username = registerEditTextUsername.getText().toString();
                    String password = registerEditTextPassword.getText().toString();
                    for (int i = 0; i < userArrayList.size(); i++) {
                        if (username.equals(userArrayList.get(i).getUsername())) {
                            isFound = true;
                        }
                    }
                    if (!isFound) {
                        User user = new User(userArrayList.size(), username, password);
                        userArrayList.add(user);
                        sqLiteManagerUser.addUserToDatabase(user);
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        loginViewModel.login(registerEditTextUsername.getText().toString(),
                                registerEditTextPassword.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "username already in use", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        //String welcome = getString(R.string.welcome) + model.getDisplayName();
        Intent intent = new Intent(this, MainActivity.class);
        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void loadFromDBToMemory(){
        try (SQLiteManagerUser sqLiteManagerUser = SQLiteManagerUser.instanceOfUserDatabase(this)) {
            sqLiteManagerUser.populateUserArrayList();
        }
    }
}