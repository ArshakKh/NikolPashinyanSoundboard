package com.example.hello.appnav;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hello.appnav.adapterDb.GameDb;
import com.example.hello.appnav.adapterDb.GameInfo;

import java.lang.reflect.Field;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    public static GameDb gameDb;
    public GameInfo gameInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        LinearLayout nikolLayout = findViewById(R.id.nikolLayout);
        LinearLayout maestroLayout = findViewById(R.id.maestroLayout);
        nikolLayout.setOnClickListener(this);
        maestroLayout.setOnClickListener(this);

        gameDb = GameDb.getInstance(this);
        gameInfo = new GameInfo();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            showStartDialog();
        }

        final Field[] resFields = R.raw.class.getFields();


        if (gameDb.gameDao().getGameInfo().size() == 0) {
            gameInfo.setAdsCount(1);
            gameInfo.setIs1(true);
            gameInfo.setFavorite(false);
            try {
                for (int c = 0; c < resFields.length; c++) {
                    gameInfo.setId(c);
                    gameInfo.setIsNikol(!resFields[c].getName().contains("_0"));
                    gameInfo.setButtonText(resFields[c].getName());
                    gameInfo.setButtonId(resFields[c].getInt(resFields));

                    gameDb.gameDao().addInfo(gameInfo);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Արդեն հնարավոր է")
                .setMessage("ձայներով կիսվել ընկերների հետ, պարզապես սեխմած պահելով ձայնի վրա և ընտրել Share կոճակը")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Rate Us:");
        alert.setMessage("Do you want rate the app?");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(i);
        });
        alert.setNegativeButton("No", (dialog, which) -> System.exit(0));
        alert.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {

        gameDb.gameDao().updateIsFirst(view.getId() == R.id.nikolLayout);

        startActivity(new Intent(WelcomeActivity.this, NikolActivity.class));

//        switch (view.getId()) {
//            case R.id.nikolLayout:
//                gameDb.gameDao().updateIsFirst(true);
//                startActivity(new Intent(WelcomeActivity.this, NikolActivity.class));
//                break;
//
//            case R.id.maestroLayout:
//                gameDb.gameDao().updateIsFirst(false);
//                startActivity(new Intent(WelcomeActivity.this, NikolActivity.class));
//        }
    }
}

