package com.example.finedustapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.spec.ECField;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private String userID;

    Button registerBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerBtn = (Button)findViewById(R.id.registerBtn);
        loginBtn = (Button)findViewById(R.id.loginBtn);

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);

        userID = idText.getText().toString();

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });




        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String ID = idText.getText().toString();
                final String password = passwordText.getText().toString();

                if(idText.getText().toString().equals("") || passwordText.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("계정을 다시 확인하세요.")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    dialog.show();
                }else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response); // 해당 결과를 받아옴
                                boolean success = jsonResponse.getBoolean("success");
                                //                       Log.e("permission = "+permission, "permission");

                                if (success) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            dialog = builder.setMessage("로그인에 성공했습니다.")
                                                    .setPositiveButton("확인", null)
                                                    .create();
                                            dialog.show();

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);  // 메인 액티비티로 넘어감
                                            intent.putExtra("userID", idText.getText().toString());
                                            startActivity(intent);

                                            finish();


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    dialog = builder.setMessage("계정을 다시 확인하세요.")
                                            .setNegativeButton("다시 시도", null)
                                            .create();
                                    dialog.show();
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(ID, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }



}