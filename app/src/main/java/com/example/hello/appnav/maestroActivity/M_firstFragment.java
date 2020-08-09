package com.example.hello.appnav.maestroActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hello.appnav.FirstFragment;
import com.example.hello.appnav.Map.JavaMap;
import com.example.hello.appnav.R;
import com.example.hello.appnav.model.FavBott;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.hello.appnav.SecondFragment.allfavBottList;


public class M_firstFragment extends Fragment {
    private MediaPlayer mp;
    private static InterstitialAd mInterstitialAd;
    private List<FavBott> favBottList;
    private FavBott favBott;
    private JavaMap javaMap;
    private int count = 0;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Use sound as");
        menu.add(0, v.getId(), 0, "Add to favorites")
                .setIcon(R.drawable.star);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Use an activity context to get the rewarded video instance.
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mInterstitialAd = new InterstitialAd(Objects.requireNonNull(getContext()));
        mInterstitialAd.setAdUnitId(getString(R.string.ad_init_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        new RequestConfiguration.Builder().setTestDeviceIds(Collections.singletonList("8F54F510AAE49F7A02AFD8CFF4BBDC1F"));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdFailedToLoad(LoadAdError error) {

                String errorDomain = error.getDomain();
                int errorCode = error.getCode();
                String errorMessage = error.getMessage();
                AdError cause = error.getCause();
                Log.d("Ads", error.toString());

            }

        });

        favBottList = allfavBottList();
        ArrayList<FavBott> favButtoms = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_m_first, container, false);
        List<String> names = Arrays.asList(
                getResources().getString(R.string.m_btn100), getResources().getString(R.string.m_btn101), getResources().getString(R.string.m_btn102), getResources().getString(R.string.m_btn103), getResources().getString(R.string.m_btn104),
                getResources().getString(R.string.m_btn105), getResources().getString(R.string.m_btn106), getResources().getString(R.string.m_btn107), getResources().getString(R.string.m_btn108), getResources().getString(R.string.m_btn109),
                getResources().getString(R.string.m_btn110), getResources().getString(R.string.m_btn111), getResources().getString(R.string.m_btn112), getResources().getString(R.string.m_btn113), getResources().getString(R.string.m_btn114),
                getResources().getString(R.string.m_btn115), getResources().getString(R.string.m_btn116), getResources().getString(R.string.m_btn117), getResources().getString(R.string.m_btn118), getResources().getString(R.string.m_btn119),
                getResources().getString(R.string.m_btn120), getResources().getString(R.string.m_btn121), getResources().getString(R.string.m_btn122), getResources().getString(R.string.m_btn123), getResources().getString(R.string.m_btn124),
                getResources().getString(R.string.m_btn125));

        int marginInPx = dpToPx(8);
        int layoutWidthInPx = dpToPx(150);
        int layoutHeightInPx = dpToPx(90);

        for (int i = 0; i < names.size(); i++) {
            Button myButton = new Button(getActivity());
            myButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.m_button), PorterDuff.Mode.MULTIPLY);
            myButton.setText(names.get(i));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(layoutWidthInPx, layoutHeightInPx);
            param.setMargins(marginInPx, marginInPx, marginInPx, marginInPx);
            myButton.setTextColor(ContextCompat.getColor(getContext(), R.color.text_light));
            myButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            myButton.setLayoutParams(param);
            myButton.setId(100 + i);
            final int finalI = 100 + i;
            javaMap = new JavaMap();

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playGiven(javaMap.getSparseIntArray(finalI));
                    count++;
                    if (count % 7 == 0) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                    }
                }
            });

            myButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v);
                    return false;
                }
            });
            LinearLayout ll = rootView.findViewById(i % 2 == 0 ? R.id.leftSide_m : R.id.rightSide_m);
            ll.addView(myButton);
        }
        return rootView;
    }

    private void showPopupWindow(final View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.botton_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {

                Button b = (Button) view;

                switch (item.toString()) {

                    case "Share":

                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestStoragePermission();
                        } else voiceShare(b.getId(), b.getText().toString());
                        break;

                    case "Add to favorites":


                        favBottList = allfavBottList();
                        List<FavBott> fbl = favBottList;


                        String buttonText = b.getText().toString();
                        if (fbl.size() > 0) {

                            boolean exist = false;
                            for (int ch = 0; ch < fbl.size(); ch++) {
                                String dbButtonText = fbl.get(ch).getButtonText();
                                if (dbButtonText.equals(buttonText)) {
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                String bot_text = b.getText().toString();
                                int bot_id = b.getId();
                                favBott = new FavBott();
                                favBott.setButtonText(bot_text);
                                favBott.setButtonId(bot_id);
                                favBott.save();
                                Toast.makeText(getActivity(), "Congratulations " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), " Sound is already add", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String bot_text = b.getText().toString();
                            int bot_id = b.getId();
                            favBott = new FavBott();
                            favBott.setButtonText(bot_text);
                            favBott.setButtonId(bot_id);
                            favBott.save();
                            Toast.makeText(getActivity(), "Congratulations " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                }
                return true;
            }
        });
        popup.show();
    }

    private int dpToPx(int dp) {
        float density = Objects.requireNonNull(getActivity()).getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    private void playGiven(int sound) {
        stopPlay();
        mp = MediaPlayer.create(getActivity(), sound);
        mp.start();
    }

    private void stopPlay() {
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }

    }

    private void voiceShare(int id, String name) {

        InputStream inputStream;
        FileOutputStream fileOutputStream;
        File dir = new File(Environment.getExternalStorageDirectory() + "/Soundboard");
        dir.mkdir();
        File file = new File(dir, name + ".mp3");
        try {
            javaMap = new JavaMap();
            inputStream = getResources().openRawResource(javaMap.getSparseIntArray(id));
            fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("file://" + file));
        intent.setType("audio/*");
        startActivity(Intent.createChooser(intent, "Share sound"));
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) Objects.requireNonNull(getContext()),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for sound share")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
