package com.example.hello.appnav.config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.hello.appnav.BuildConfig;
import com.example.hello.appnav.Map.TranslationMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ClassAdapter extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    TranslationMap translationMap;
    private final int STORAGE_PERMISSION_CODE = 1;

    public void MPStop() {
        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    public void playGiven(Context context, int sound) {
        MPStop();
        mp = MediaPlayer.create(context, sound);
        mp.start();
    }

    public void voiceShare(Context context, int id, String name) {

        translationMap = new TranslationMap(context);

        InputStream inputStream;
        FileOutputStream fileOutputStream;
        File dir = new File(context.getExternalFilesDir("Soundboard") + java.io.File.separator + "/");
        dir.mkdir();
        String translatedName = translationMap.getButtonName(name);
        File file = new File(dir, translatedName + ".mp3");
        try {
            inputStream = context.getResources().openRawResource(id);
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


        Uri uri;

        if (Build.VERSION.SDK_INT <= 24) {
            uri = Uri.parse("file://" + file);
        } else {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent chooserIntent = Intent.createChooser(intent, "Share sound");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooserIntent);

    }

    public void requestStoragePermission(Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for share sounds")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ClassAdapter.this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ClassAdapter.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
