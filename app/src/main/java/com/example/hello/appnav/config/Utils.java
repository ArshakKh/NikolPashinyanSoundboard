package com.example.hello.appnav.config;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.List;

public class Utils extends ViewModel {

    public MutableLiveData<List<GridItem>> getGridData() {
        MutableLiveData<List<GridItem>> mGridData = new MutableLiveData<>();
        mGridData.postValue(loadAllSigns());
        return mGridData;
    }



    public static ArrayList<GridItem> loadAllSigns() {

        ArrayList<GridItem> gridItems = new ArrayList<>();
        gridItems.add(new GridItem("Մարդա վատացե", "nikol", 0));
        gridItems.add(new GridItem("էնտեղելա մարդ վատացել", "nikol", 0));
        gridItems.add(new GridItem("Հանգիստ Նստեք տեղներդ", "", 0));
        return gridItems;
    }
}
