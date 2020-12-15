package com.example.hello.appnav;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.Collections;

import hotchemi.android.rate.AppRate;

public class NikolActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private boolean isFabTapped = true;

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }

        return dir.delete();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nikol);

        floatingActionButton = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.replaceMenu(R.menu.main_manu);
        bottomAppBar.setOnMenuItemClickListener(menuItem -> {
            String url = "https://docs.google.com/forms/d/e/1FAIpQLSfW6JpCt2I68iMsEsR4s3hIxSgzi6gtGTppGWqCLJb1R0aK6g/viewform?usp=pp_url";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return false;
        });
        //// Share_Btn ////
        bottomAppBar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBudy = "\nLet me recommend you this application\n";
            String shareSub = "Nicol Pashinyan SoundDesk";
            shareBudy = shareBudy + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            intent.putExtra(Intent.EXTRA_TEXT, shareBudy);
            startActivity(Intent.createChooser(intent, "Share the Great App"));
        });

        //// Ad Code ////

        Appodeal.initialize(this, getString(R.string.APPODEAL_APP_KEY),
                Appodeal.BANNER, true);

        new RequestConfiguration.Builder().setTestDeviceIds(Collections.singletonList("8F54F510AAE49F7A02AFD8CFF4BBDC1F"));
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.show(this, Appodeal.BANNER_TOP);

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {

            }

            @Override
            public void onBannerFailedToLoad() {
                Log.i("ADS", "onBannerFailedToLoad: ");
            }

            @Override
            public void onBannerShown() {

            }

            @Override
            public void onBannerShowFailed() {
                Log.i("ADS", "onBannerShowFailed: ");
            }

            @Override
            public void onBannerClicked() {

            }

            @Override
            public void onBannerExpired() {

            }
        });

        if (savedInstanceState == null) {
            handleFrame(new FirstFragment());
        }
        handleFab();


        //// Rate us ////
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        FirebaseAnalytics.getInstance(this);
    }

    private void handleFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame1, fragment);
        fragmentTransaction.commit();
    }

    private void handleFab() {
        floatingActionButton.setOnClickListener(v -> {
            if (isFabTapped) {
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                handleFrame(new SecondFragment(true));
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_reply_black_24dp));
            } else {
                bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
                handleFrame(new FirstFragment());
                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.star));
            }
            isFabTapped = !isFabTapped;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        File dir = new File(NikolActivity.this.getExternalFilesDir("Soundboard") + "/");
        if (deleteDir(dir)) {
            deleteDir(dir);
        }
        Appodeal.show(this, Appodeal.BANNER_TOP);
    }
}
