package com.example.hello.appnav.adapterDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gameDb")
public class GameInfo {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "button_id")
    private int buttonId;

    @ColumnInfo(name = "button_text")
    private String buttonText;

    @ColumnInfo(name = "ads_count")
    private int adsCount;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;
    @ColumnInfo(name = "is_nikol")
    private boolean isNikol;
    @ColumnInfo(name = "is_1")
    private boolean is1;


    public boolean isIs1() { return is1; }

    public void setIs1(boolean is1) { this.is1 = is1; }

    public boolean isNikol() {
        return isNikol;
    }

    public void setIsNikol(boolean nikol) {
        isNikol = nikol;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getAdsCount() {
        return adsCount;
    }

    public void setAdsCount(int adsCount) {
        this.adsCount = adsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

}
