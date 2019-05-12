package com.example.hello.appnav;

import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.example.hello.appnav.Map.JavaMap;
import com.example.hello.appnav.model.FavBott;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.hello.appnav.SecondFragment.allfavBottList;

public class FirstFragment extends Fragment {
    public static InterstitialAd mInterstitialAd;
    FavBott favBott;
    JavaMap javaMap;
    int count = 0;
    private MediaPlayer mp;
    private List<FavBott> favBottList;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Use sound as");
        menu.add(0, v.getId(), 0, "Add to favorites")
                .setIcon(R.drawable.star);
        MobileAds.initialize(getContext(), "ca-app-pub-5997286771009050~9914555568");

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Use an activity context to get the rewarded video instance.
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5997286771009050/1129467542");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        favBottList = allfavBottList();
        ArrayList<FavBott> favButtoms = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        List<String> names = Arrays.asList(getResources().getString(R.string.btn0), getResources().getString(R.string.btn1), getResources().getString(R.string.btn2), getResources().getString(R.string.btn3), getResources().getString(R.string.btn4), getResources().getString(R.string.btn5), getResources().getString(R.string.btn6), getResources().getString(R.string.btn7), getResources().getString(R.string.btn8), getResources().getString(R.string.btn9), getResources().getString(R.string.btn10),
                getResources().getString(R.string.btn11), getResources().getString(R.string.btn12), getResources().getString(R.string.btn13), getResources().getString(R.string.btn14), getResources().getString(R.string.btn15), getResources().getString(R.string.btn16), getResources().getString(R.string.btn17), getResources().getString(R.string.btn18), getResources().getString(R.string.btn19), getResources().getString(R.string.btn20),
                getResources().getString(R.string.btn21), getResources().getString(R.string.btn22), getResources().getString(R.string.btn23), getResources().getString(R.string.btn24), getResources().getString(R.string.btn25), getResources().getString(R.string.btn26), getResources().getString(R.string.btn27), getResources().getString(R.string.btn28), getResources().getString(R.string.btn29), getResources().getString(R.string.btn30),
                getResources().getString(R.string.btn31), getResources().getString(R.string.btn32), getResources().getString(R.string.btn33), getResources().getString(R.string.btn34), getResources().getString(R.string.btn35), getResources().getString(R.string.btn36), getResources().getString(R.string.btn37), getResources().getString(R.string.btn38), getResources().getString(R.string.btn39), getResources().getString(R.string.btn40),
                getResources().getString(R.string.btn41), getResources().getString(R.string.btn42), getResources().getString(R.string.btn43), getResources().getString(R.string.btn44), getResources().getString(R.string.btn45), getResources().getString(R.string.btn46), getResources().getString(R.string.btn47), getResources().getString(R.string.btn48));
        for (int i = 0; i < names.size(); i++) {
            Button myButton = new Button(getActivity());
            myButton.getBackground().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.MULTIPLY);
            myButton.setText(names.get(i));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(150), dpToPx(90));
            param.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            myButton.setTextColor(getResources().getColor(R.color.text_light));
            myButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            myButton.setLayoutParams(param);
            myButton.setId(i);
            final int finalI = i;
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
            LinearLayout ll = rootView.findViewById(i % 2 == 0 ? R.id.leftSide : R.id.rightSide);
            ll.addView(myButton);
        }
        return rootView;
    }

    public int dpToPx(int dp) {
        float density = getActivity().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void playGiven(int sound) {
        stopPlay();
        mp = MediaPlayer.create(getActivity(), sound);
        mp.start();
    }

    public void stopPlay() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

    }

    void showPopupWindow(final View view) {
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
                favBottList = allfavBottList();
                List<FavBott> fbl = favBottList;

                Button b = (Button) view;
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


                return true;
            }

//


        });
        popup.show();
    }

}
