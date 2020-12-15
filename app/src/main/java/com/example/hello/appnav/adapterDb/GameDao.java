package com.example.hello.appnav.adapterDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    void addInfo(GameInfo game);

    @Query("select * from gameDb")
    List<GameInfo> getGameInfo();


    @Query("SELECT * FROM gameDb WHERE is_favorite = :isFavorite")
    List<GameInfo> getFavorites(boolean isFavorite);

    @Query("SELECT * FROM gameDb WHERE is_nikol = :isNikol")
    List<GameInfo> isNikol(boolean isNikol);

    @Update
    void updateGame(GameInfo gameInfo);

    @Query("Update gameDb SET ads_count = :id")
    void updateAdsCount(int id);

    @Query("Update gameDb SET is_1 = :is_1")
    void updateIsFirst(boolean is_1);

    @Query("Update gameDb SET is_favorite = :isFavorite Where button_id = :buttonId")
    void updateFavorite(int buttonId, boolean isFavorite);

    @Query("Update gameDb SET is_nikol = :isNikol Where button_id = :buttonId")
    void updateStatus(int buttonId, boolean isNikol);
}

