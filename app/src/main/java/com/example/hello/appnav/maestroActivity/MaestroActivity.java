package com.example.hello.appnav.maestroActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.hello.appnav.BuildConfig;
import com.example.hello.appnav.FirstFragment;
import com.example.hello.appnav.R;
import com.example.hello.appnav.SecondFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import hotchemi.android.rate.AppRate;

public class MaestroActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private boolean isFabTapped = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maestro);

        floatingActionButton = findViewById(R.id.fab_m);
        bottomAppBar = findViewById(R.id.bottomAppBar_m);
        bottomAppBar.replaceMenu(R.menu.main_manu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.suggest_btn:
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSfW6JpCt2I68iMsEsR4s3hIxSgzi6gtGTppGWqCLJb1R0aK6g/viewform?usp=pp_url";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
//                        break;
//                    case R.id.about_us:
//                        break;
//                }
                return false;
            }
        });
        //// Share_Btn ////
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBudy = "\nLet me recommend you this application\n";
                String shareSub = "Nicol Pashinyan SoundDesk";
                shareBudy = shareBudy + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBudy);
                startActivity(Intent.createChooser(intent, "Share the Great App"));
            }
        });

        //// AdMob Code ////
        MobileAds.initialize(this,"ca-app-pub-5997286771009050~9914555568");
        AdView mAdView = findViewById(R.id.adView_m);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (savedInstanceState == null) {
            handleFrame(new M_firstFragment());
        }handleFab();

        //// Rate us ////
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
    }
    private void handleFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame1_m, fragment);
        fragmentTransaction.commit();
    }
    private void handleFab(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFabTapped) {
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                    handleFrame(new M_secondFragment());
                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_reply_white_24dp));
                } else {
                    bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                    handleFrame(new M_firstFragment());
                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star_white));
                }
                isFabTapped = !isFabTapped;
            }
        });
    }
}

