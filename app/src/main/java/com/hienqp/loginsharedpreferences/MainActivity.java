package com.hienqp.loginsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextPassword;
    CheckBox checkBoxRememberLogin;
    Button buttonLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String systemUserName = "babebap";
    private static final String systemPassword = "30092019";
    private static final String successfulLoginNotification = "Đăng nhập thành công";
    private static final String failedLoginNotification = "Đăng nhập thất bại";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureObjectViewFromLayout();

        // get Instance Shared Preferences
        getInstanceOfSharedPreferences();

        // get Data from Shared Preferences
        getDataFromSharedPreferences();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginSession();
            }
        });
    }

    private void getInstanceOfSharedPreferences() {
        sharedPreferences = getSharedPreferences(SharedPreferencesInformation.DATA_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void captureObjectViewFromLayout() {
        editTextUserName = (EditText) findViewById(R.id.editText_user_name);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        checkBoxRememberLogin = (CheckBox) findViewById(R.id.checkBox_remember_login);
        buttonLogin = (Button) findViewById(R.id.button_login);
    }

    private void checkLoginSession() {
        String userName = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (userName.equals(systemUserName) && password.equals(systemPassword)) {
            Toast.makeText(MainActivity.this, successfulLoginNotification, Toast.LENGTH_SHORT).show();

            // kiểm tra user có check vào tùy chọn lưu tài khoản mật khẩu cho lần đăng nhập sau hay không
            if (checkBoxRememberLogin.isChecked()) {
                putDataLoginIntoSharedPreferences(userName, password);
            } else {
                removeDataLoginFromSharedPreferences();
            }
        } else {
            Toast.makeText(MainActivity.this, failedLoginNotification, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeDataLoginFromSharedPreferences() {
        editor.remove(SharedPreferencesInformation.USER_NAME_SHARED_PREFERENCES);
        editor.remove(SharedPreferencesInformation.PASSWORD_SHARED_PREFERENCES);
        editor.remove(SharedPreferencesInformation.IS_CHECKED_SHARED_PREFERENCES);
        editor.apply();
    }

    private void putDataLoginIntoSharedPreferences(String userName, String password) {
        editor.putString(SharedPreferencesInformation.USER_NAME_SHARED_PREFERENCES, userName);
        editor.putString(SharedPreferencesInformation.PASSWORD_SHARED_PREFERENCES, password);
        editor.putBoolean(SharedPreferencesInformation.IS_CHECKED_SHARED_PREFERENCES, true);
        editor.apply();
    }

    private void getDataFromSharedPreferences() {
        editTextUserName.setText(sharedPreferences.getString(SharedPreferencesInformation.USER_NAME_SHARED_PREFERENCES, ""));
        editTextPassword.setText(sharedPreferences.getString(SharedPreferencesInformation.PASSWORD_SHARED_PREFERENCES, ""));
        checkBoxRememberLogin.setChecked(sharedPreferences.getBoolean(SharedPreferencesInformation.IS_CHECKED_SHARED_PREFERENCES, false));
    }
}