package com.example.loginapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginapi.api.ApiService;
import com.example.loginapi.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Uri uri;
    private String uid;
    private String p;
    private String pw;
    private String UserName;
    private String PassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = findViewById(R.id.edt_user);
        edtPass = findViewById(R.id.edt_pass);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    private void checkLogin() {
        UserName = edtUser.getText().toString().trim();
        PassWord = edtPass.getText().toString().trim();
        getListUser(UserName, PassWord);
    }

    private void getListUser(String user, String pass) {
        ApiService.apiService.getListUser(user, pass)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//
                        if (response.isSuccessful() && response.body() != null) {
                            String responseBody = response.body();

                            String modifiedJsonString = responseBody.replace(",\"5.0.0.2\",\"Công ty TNHH Đầu tư SX & TM Hoàng Minh\"", "");

                            // Kiểm tra nếu dữ liệu trả về là một mảng JSON
                            if (modifiedJsonString.startsWith("[")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                                List<User> userList = new ArrayList<>();

                                try {
                                    JSONArray jsonArray = new JSONArray(modifiedJsonString);

                                    // lấy ra vị trí object nào
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        User user = new Gson().fromJson(jsonObject.toString(), User.class);
                                        userList.add(user);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("List User", "Lỗi JSON: " + e.getMessage());
                                }
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Link API không đúng sẽ thông báo
                            Toast.makeText(LoginActivity.this, "API lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "API lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
