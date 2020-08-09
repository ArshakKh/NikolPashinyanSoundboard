package com.example.hello.appnav.maestroActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hello.appnav.BuildConfig;
import com.example.hello.appnav.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Collections;

import hotchemi.android.rate.AppRate;

import static com.activeandroid.Cache.getContext;
import static com.example.hello.appnav.NikolActivity.deleteDir;

public class MaestroActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private boolean isFabTapped = true;
    private File dir = new File(Environment.getExternalStorageDirectory() + "/Soundboard");

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
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSfW6JpCt2I68iMsEsR4s3hIxSgzi6gtGTppGWqCLJb1R0aK6g/viewform?usp=pp_url";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView_m);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        new RequestConfiguration.Builder().setTestDeviceIds(Collections.singletonList("8F54F510AAE49F7A02AFD8CFF4BBDC1F"));

        if (savedInstanceState == null) {
            handleFrame(new M_firstFragment());
        }
        handleFab();

        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError error) {
                String errorDomain = error.getDomain();
                int errorCode = error.getCode();
                String errorMessage = error.getMessage();
                AdError cause = error.getCause();
                Log.d("Ads", error.toString());
            }

        });

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

    private void handleFab() {
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

    @Override
    protected void onResume() {
        super.onResume();
        if (deleteDir(dir)) {
            deleteDir(dir);
        }
    }
}

