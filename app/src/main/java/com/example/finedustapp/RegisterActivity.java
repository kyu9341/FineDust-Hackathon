package com.example.finedustapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private String ID;
    private String password;
    private String name;
    private String phone;
    private AlertDialog dialog; // 알림창
    private boolean validate = false; // 사용할 수 있는 ID인지 체크해주는 변수


    Button cancelBtn;
    Button completeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);
        final EditText nameText = (EditText)findViewById(R.id.nameText);
        final EditText phoneText = (EditText)findViewById(R.id.phoneText);


        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        completeBtn = (Button)findViewById(R.id.completeBtn);

        final Button validateBtn = (Button)findViewById(R.id.validateBtn);
        validateBtn.setOnClickListener(new View.OnClickListener() {  // 중복확인 시 이벤트 처리
            @Override
                    public void onClick(View view) {
                        Log.e("아아아","들어왔냐");
                        String ID = idText.getText().toString();
                        if(validate){ // 중복확인이 이루어져 있다면
                            Log.e("아아아","들어왔냐11");
                    return;   //함수 종료
                }
                if(ID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        }

                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
                Log.e("아아아","들어왔냐22");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() { // 사용자 가입 시 회원가입 후 로그인페이지로 이동
            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();
                password = passwordText.getText().toString();
                name = nameText.getText().toString();
                phone = phoneText.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("먼저 중복체크를 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;

                }if(ID.equals("")||password.equals("")||name.equals("")||phone.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 회원 등록에 성공했을 경우 성공 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                                //                               finish();
                                Intent completeIntent = new Intent(getApplicationContext(), MainActivity.class); // 로그인 화면으로 돌아감
                                completeIntent.putExtra("userID", ID);
                                startActivity(completeIntent);
                                Log.e("userID = ",ID+"userID");
                                finish();
                            }else{ // 회원 등록에 실패한 경우 실패 알림창 출력
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();

                            }
                        }
                        catch (Exception e){
                            e.printStackTrace(); // 오류 출력
                        }

                    }
                } ;
                RegisterRequest registerRequest = new RegisterRequest(ID, password, name, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
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
