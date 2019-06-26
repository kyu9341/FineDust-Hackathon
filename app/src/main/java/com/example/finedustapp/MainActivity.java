package com.example.finedustapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private String cityName; // 지역 이름
    private String dataTime; // 측정 시간
    private int pm10Value; // 미세먼지 농도
    private int pm25Value; // 초미세먼지 농도
    TextView regionName, pm10Text, pm25Text;
    TextView userIDText;
    public static String userID;
    public final String idKey = "userID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regionName = (TextView)findViewById(R.id.regionName);
        Button regionSetting = (Button)findViewById(R.id.regionSetting);
        Button boxLocation = (Button)findViewById(R.id.boxLocation);
        pm10Text = (TextView)findViewById(R.id.pm10Text);
        pm25Text = (TextView)findViewById(R.id.pm25Text);
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        userIDText = (TextView)findViewById(R.id.userIDText);
        Button cardBtn = (Button)findViewById(R.id.cardBtn);


        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Log.e("userID = ",userID+"userID");
        // 지난번 저장해놨던 사용자 입력값을 꺼내서 보여주기
        SharedPreferences sf = getSharedPreferences(idKey, 0);
        String str = sf.getString("name", ""); // 키값으로 꺼냄
            userIDText.setText(userID+"님 환영합니다");

   //     if(userID.getBytes().length < 0){}

        if(str.equals("") && userID == null){
            userIDText.setText("먼저 로그인을 해주세요");
        }else{

        //    userIDText.setText(userID+"님 환영합니다");
            if(str.equals("")){

            }else{
                if(userID == null){

                    userIDText.setText(str+"님 환영합니다");
                    userID = str;
                }else
                userIDText.setText(userID+"님 환영합니다");
            }

        }


/*
        if(userID.equals(str)){
            userIDText.setText(str+"님 환영합니다");
            userID = str;
        }


             else{
                userIDText.setText(userID+"님 환영합니다");

        }
*/


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        regionSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegionSettingActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        boxLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onStop() {
        super.onStop();
        // Activity 가 종료되기 전에 저장한다
        // SharedPreferences 에 설정값(특별히 기억해야할 사용자 값)을 저장하기
        SharedPreferences sf = getSharedPreferences(idKey, 0);
        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요


        //      String str = et.getText().toString(); // 사용자가 입력한 값
        editor.putString("name", userID); // 입력
        editor.commit(); // 파일에 최종 반영함
    }
 // end of class




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                cityName = data.getStringExtra("Region");
                regionName.setText(cityName);
                new MainActivity.BackgroundTask().execute(); //db 연동

                Log.e("region = ", cityName + "region");
            }
//        } else if (requestCode == REQUEST_ANOTHER) {
//            ...
        }
    }

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK){
                    Intent intent = getIntent();
                    cityName = intent.getStringExtra("Region");
                    regionName.setText("cityName");
                    Log.e("region = ", cityName + "region");
                }

        }

    */
    class BackgroundTask extends AsyncTask<Void, Void, String> //
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/dust1.php?cityName="+ URLEncoder.encode(cityName, "UTF-8"); // GET 방식으로 인덱스를 서버에 전송
                Log.e("cityName = ", cityName + "cityName");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); // 넘어오는 결과값들을 저장
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 해당 inputstream에 있는 내용들을 버퍼에 담아 읽을수 있도록 함.
                String temp;
                StringBuilder stringBuilder = new StringBuilder(); // 문자열 형태로 저장
                while ((temp = bufferedReader.readLine()) != null){  // 버퍼에서 받아오는 값을 한줄씩 읽으면 temp에 저장
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){ // 해당 결과 처리
            try{
                JSONObject jsonObject = new JSONObject(result); // 응답 부분 처리
                JSONArray jsonArray = jsonObject.getJSONArray("response");


                JSONObject object = jsonArray.getJSONObject(0); // 현재 배열의 원소값

                dataTime = object.getString("dataTime");
                pm10Value = object.getInt("pm10Value");
                pm25Value = object.getInt("pm25Value");

                if(pm10Value>0 && pm10Value<=30){
                    pm10Text.setText("좋음" + "("+pm10Value+"ug/m^2)");
                }else if(pm10Value>30 && pm10Value<=80){
                    pm10Text.setText("보통" + "("+pm10Value+"ug/m^2)");
                }else if(pm10Value>80 && pm10Value<=150){
                    pm10Text.setText("나쁨" + "("+pm10Value+"ug/m^2)");
                }else if(pm10Value>150){
                    pm10Text.setText("매우나쁨" + "("+pm10Value+"ug/m^2)");
                }



                if(pm25Value>0 && pm25Value<=15){
                    pm25Text.setText("좋음" + "("+pm25Value+"ug/m^2)");
                }else if(pm25Value>15 && pm25Value<=50){
                    pm25Text.setText("보통" + "("+pm25Value+"ug/m^2)");
                }else if(pm25Value>50 && pm25Value<=100){
                    pm25Text.setText("나쁨" + "("+pm25Value+"ug/m^2)");
                }else if(pm25Value>100){
                    pm25Text.setText("매우나쁨" + "("+pm25Value+"ug/m^2)");
                }



//                pm10Text.setText(pm10Value+"");
 //               pm25Text.setText(pm25Value+"");
                Log.e("dataTime = ", dataTime + "dataTime");
                Log.e("pm10Value = ", pm10Value + "pm10Value");
                Log.e("pm25Value = ", pm25Value + "pm25Value");


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}

