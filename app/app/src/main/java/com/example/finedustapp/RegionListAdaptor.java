package com.example.finedustapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RegionListAdaptor extends BaseAdapter {
    private Context context;
    private List<Region> RegionList;

    public RegionListAdaptor(Context context, List<Region> RegionList){
        this.context = context;
        this.RegionList = RegionList;
    }

    @Override
    public int getCount() {
        return RegionList.size();
    }

    @Override
    public Object getItem(int position) {
        return RegionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View v = View.inflate(context, R.layout.region, null); // 레이아웃 참조
        TextView regionName = (TextView)v.findViewById(R.id.regionName);

        regionName.setText(RegionList.get(position).getRegionName());
        v.setTag(RegionList.get(position).getRegionName());
        return v;
    }
}
