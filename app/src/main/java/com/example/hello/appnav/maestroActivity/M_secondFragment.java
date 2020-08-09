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
import android.util.TypedValue;
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

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.hello.appnav.Map.JavaMap;
import com.example.hello.appnav.R;
import com.example.hello.appnav.model.FavBott;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class M_secondFragment extends Fragment {
    private FavBott favBott;
    private View rootView;
    private MediaPlayer mp;
    private JavaMap javaMap;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_m_second, container, false);
        renderButtons();
        return rootView;
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

    private static List<FavBott> allfavBottList() {
        return new Select()
                .from(FavBott.class)
                .execute();
    }

    private void renderButtons() {
        ArrayList<FavBott> fabButtons = new ArrayList<>();
        favBott = new FavBott();
        List<FavBott> favBottList = allfavBottList();
        for (int i = 0; i < favBottList.size(); i++) {
            favBott = favBottList.get(i);
            fabButtons.add(favBott);
            final int btId = favBott.getButtonId();
            Button myButton = new Button(getActivity());
            myButton.getBackground().setColorFilter(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.m_button), PorterDuff.Mode.MULTIPLY);
            myButton.setText(favBott.getButtonText());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(dpToPx(150), dpToPx(90));
            param.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            myButton.setTextColor(ContextCompat.getColor(getContext(),R.color.text_light));
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
            LinearLayout ll = rootView.findViewById(i % 2 == 0 ? R.id.outLeftSide_m : R.id.outRightSide_m);
            ll.addView(myButton);
        }
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

        popup.getMenuInflater().inflate(R.menu.second_buttom_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                Button b = (Button) view;
                String buttonText = b.getText().toString();
                int soundId = 0;

                try {
                    for (int i = 0; i <= allfavBottList().size(); i++) {
                        if (allfavBottList().get(i).getButtonText().equals(buttonText)) {
                            soundId = allfavBottList().get(i).buttonId;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                switch (item.toString()) {

                    case "Share":

                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestStoragePermission();
                        } else voiceShare(soundId, buttonText);
                        break;

                    case "Remove from favorites":
                        favBott = new FavBott();
                        new Delete().from(FavBott.class).where("bot_text = ?", buttonText).execute();
                        setUserVisibleHint();
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popup.show();
    }

    private void setUserVisibleHint() {
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
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

