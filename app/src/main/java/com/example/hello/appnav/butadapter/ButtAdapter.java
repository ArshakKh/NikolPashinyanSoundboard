package com.example.hello.appnav.butadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.hello.appnav.model.FavBott;

import java.util.ArrayList;

public class ButtAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FavBott> buttonArrayList;

    public ButtAdapter(Context context, ArrayList<FavBott> buttonArrayList) {
        this.context = context;
        this.buttonArrayList = buttonArrayList;
    }

    @Override
    public int getCount() {
        return buttonArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return buttonArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
        }



        return convertView;
    }




}
