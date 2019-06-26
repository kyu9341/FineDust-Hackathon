package com.example.finedustapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CardActivity extends AppCompatActivity {

    TextView cardNumText;
    private String cardNum;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        cardNumText = (TextView)findViewById(R.id.cardNumText);
        Button cardBtn = (Button)findViewById(R.id.cardBtn);

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNum = cardNumText.getText().toString();

                new CardActivity.BackgroundTask().execute();
      //          Log.e("cardNum = ", cardNum + "   cardNum");
     //           Log.e("MainActivity.userID = ", MainActivity.userID + "   MainActivity.userID");
            }
        });



    }



    class BackgroundTask extends AsyncTask<Void, Void, String> //
    {
        String target;

        @Override
        protected void onPreExecute(){
            try{
                target = "http://kyu9341.cafe24.com/dust1.php?RegisterCard="+ URLEncoder.encode(cardNum, "UTF-8")+"&ID="+URLEncoder.encode(MainActivity.userID, "UTF-8"); // GET 방식으로 인덱스를 서버에 전송
                Log.e("cardNum = ", cardNum + "  //cardNum");
                Log.e("MainActivity.userID = ", MainActivity.userID + "  //MainActivity.userID");

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

     //           JSONArray jsonArray = jsonObject.getJSONArray("response");

        //        JSONObject object = jsonArray.getJSONObject(0); // 현재 배열의 원소값
                boolean success = jsonObject.getBoolean("success");


                if(success){

                    AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
                    dialog = builder.setMessage("카드 등록에 성공했습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);  // 메인 액티비티로 넘어감
                    intent.putExtra("userID", cardNum);
                    startActivity(intent);

                    finish();
                }




            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }



}
