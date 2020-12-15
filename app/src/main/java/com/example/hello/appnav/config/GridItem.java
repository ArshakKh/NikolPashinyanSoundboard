package com.example.hello.appnav.config;

import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable {

    public static final Creator<GridItem> CREATOR = new Creator<GridItem>() {
        @Override
        public GridItem createFromParcel(Parcel in) {
            return new GridItem(in);
        }

        @Override
        public GridItem[] newArray(int size) {
            return new GridItem[size];
        }
    };

    String name;
    String detail;
    int imageId;

    public GridItem(String name, String detail, int imageId) {
        this.name = name;
        this.detail = detail;
        this.imageId = imageId;
    }

    protected GridItem(Parcel in) {
        name = in.readString();
        detail = in.readString();
        imageId = in.readInt();
    }

    public int getGridImage() {
        return imageId;
    }

    public String getGridName() {
        return name;
    }

    public String getGridDetail() {
        return detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(detail);
        parcel.writeInt(imageId);
    }
}
