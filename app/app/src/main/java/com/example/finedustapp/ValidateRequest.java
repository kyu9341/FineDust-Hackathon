package com.example.finedustapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private String URL = "http://kyu9341.cafe24.com/DustValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String ID, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null); // 해당 URL에 POST 방식으로 전송
        parameters = new HashMap<>(); // HashMap으로 초기화
        parameters.put("ID", ID);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;

    }

}
