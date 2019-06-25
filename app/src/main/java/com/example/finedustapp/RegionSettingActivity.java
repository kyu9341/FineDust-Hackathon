package com.example.finedustapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RegionSettingActivity extends AppCompatActivity {



    private ListView regionListView;
    private RegionListAdaptor adapter;
    private List<Region> RegionList;
    private String[] region= new String[] {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_setting);

        regionListView = (ListView)findViewById(R.id.RegionListView);
        RegionList = new ArrayList<Region>(); // 배열에 넣어줌

        adapter = new RegionListAdaptor(getApplicationContext(), RegionList);
        regionListView.setAdapter(adapter); //리스트 뷰에 어댑터 매칭

        for(int i=0; i<region.length; i++){
            Region regionName = new Region(region[i]);
            RegionList.add(regionName);
            adapter.notifyDataSetChanged();

        }

        regionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Region", RegionList.get(position).RegionName);
                Log.e("region = "+RegionList.get(position).RegionName, "region");
                setResult(RESULT_OK,intent);
                finish();


            }
        });



    }
}
