package com.example.hello.appnav.maestroActivity;

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
import com.example.hello.appnav.R;
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


public class M_firstFragment extends Fragment {
    private MediaPlayer mp;
    public static InterstitialAd mInterstitialAd;
    private List<FavBott> favBottList;
    FavBott favBott;
    JavaMap javaMap;
    int count = 0;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Use sound as");
        menu.add(0,v.getId(), 0, "Add to favorites")
                .setIcon(R.drawable.star);
        MobileAds.initialize(getContext(),"ca-app-pub-5997286771009050~9914555568");

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Use an activity context to get the rewarded video instance.
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5997286771009050/2462251587");
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
        View rootView = inflater.inflate(R.layout.fragment_m_first, container, false);
        List<String> names = Arrays.asList(
                getResources().getString(R.string.m_btn100),getResources().getString(R.string.m_btn101),getResources().getString(R.string.m_btn102),getResources().getString(R.string.m_btn103),getResources().getString(R.string.m_btn104),
                getResources().getString(R.string.m_btn105), getResources().getString(R.string.m_btn106),getResources().getString(R.string.m_btn107),getResources().getString(R.string.m_btn108),getResources().getString(R.string.m_btn109),
                getResources().getString(R.string.m_btn110), getResources().getString(R.string.m_btn111),getResources().getString(R.string.m_btn112),getResources().getString(R.string.m_btn113),getResources().getString(R.string.m_btn114),
                getResources().getString(R.string.m_btn115), getResources().getString(R.string.m_btn116),getResources().getString(R.string.m_btn117),getResources().getString(R.string.m_btn118),getResources().getString(R.string.m_btn119),
                getResources().getString(R.string.m_btn120), getResources().getString(R.string.m_btn121),getResources().getString(R.string.m_btn122),getResources().getString(R.string.m_btn123),getResources().getString(R.string.m_btn124),
                getResources().getString(R.string.m_btn125));
        for (int i = 0; i < names.size(); i++) {
            Button myButton = new Button(getActivity());
            myButton.getBackground().setColorFilter(getResources().getColor(R.color.m_button),PorterDuff.Mode.MULTIPLY);
            myButton.setText(names.get(i));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(150), dpToPx(90));
            param.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            myButton.setTextColor(getResources().getColor(R.color.text_light));
            myButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            myButton.setLayoutParams(param);
            myButton.setId(100+i);
            final int finalI = 100+i;
            javaMap = new JavaMap();

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playGiven(javaMap.getSparseIntArray(finalI));
                    count ++;
                    if (count %7 == 0){
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
    public void stopPlay(){
        if (mp != null){
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

                Button b = (Button)view;
                String buttonText = b.getText().toString();
                if (fbl.size() > 0) {

                    Boolean exist = false;
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
                }
                else{
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
        });
        popup.show();
    }
}
