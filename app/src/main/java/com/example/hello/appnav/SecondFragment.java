package com.example.hello.appnav;

import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.hello.appnav.Map.JavaMap;
import com.example.hello.appnav.model.FavBott;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {
    FavBott favBott;
    private View rootView;
    MediaPlayer mp;
    JavaMap javaMap;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_second, container, false);
        renderButtons();
        return rootView;
    }

    public void playGiven(int sound) {
        stopPlay();
        mp = MediaPlayer.create(getActivity(), sound);
        mp.start();
    }
    private void stopPlay() {
        if (mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }
    public static List<FavBott> allfavBottList(){
        return new Select()
                .from(FavBott.class)
                .execute();
    }
    public int dpToPx(int dp) {
        float density = getActivity().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    public void renderButtons() {
        ArrayList<FavBott> fabButtons = new ArrayList<>();
        favBott = new FavBott();
        List<FavBott> favBottList = allfavBottList();
        for(int i = 0; i < favBottList.size(); i++ ){
            favBott = favBottList.get(i);
            fabButtons.add(favBott);
            final int btId = favBott.getButtonId();
            Button myButton = new Button(getActivity());
            myButton.getBackground().setColorFilter(getResources().getColor(R.color.button),PorterDuff.Mode.MULTIPLY);
            myButton.setText(favBott.getButtonText());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(150), dpToPx(90));
            param.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            myButton.setTextColor(getResources().getColor(R.color.text_light));
            myButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            myButton.setLayoutParams(param);
            myButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupWindow(v);
                    return false;
                }
            });

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    javaMap = new JavaMap();
                    playGiven(javaMap.getSparseIntArray(btId));
                }
            });
            LinearLayout ll =  rootView.findViewById(i % 2 == 0 ? R.id.outLeftSide : R.id.outRightSide);
            ll.addView(myButton);
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

        popup.getMenuInflater().inflate(R.menu.second_buttom_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Button b = (Button) view;
                favBott = new FavBott();
                        String bt = b.getText().toString();
                        new Delete().from(FavBott.class).where("bot_text = ?", bt).execute();
                        setUserVisibleHint();
                        Toast.makeText(getActivity(), "Congratulations " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popup.show();
    }
    public void setUserVisibleHint() {
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}