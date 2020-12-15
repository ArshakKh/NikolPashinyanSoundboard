package com.example.hello.appnav.viewAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.example.hello.appnav.Map.TranslationMap;
import com.example.hello.appnav.R;
import com.example.hello.appnav.adapterDb.GameDb;
import com.example.hello.appnav.adapterDb.GameInfo;
import com.example.hello.appnav.config.ClassAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ButtonItemAdapter extends RecyclerView.Adapter<ButtonItemAdapter.SignViewHolder> {

    public static GameDb gameDb;
    public static TranslationMap translationMap;
    private final boolean isNikol;
    private final boolean isFavorite;
    private final Context context;
    List<GameInfo> gameInfoList;
    List<GameInfo> getFavorites;


    public ButtonItemAdapter(Context context, boolean isNikol, boolean isFavorite) {
        this.isNikol = isNikol;
        this.context = context;
        this.isFavorite = isFavorite;

        gameDb = GameDb.getInstance(context);

        gameInfoList = gameDb.gameDao().isNikol(isNikol);
        getFavorites = gameDb.gameDao().getFavorites(isFavorite);

        translationMap = new TranslationMap(context);

        Appodeal.initialize((Activity) context, context.getString(R.string.APPODEAL_APP_KEY),
                Appodeal.INTERSTITIAL, true);
        Appodeal.setTesting(true);
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                // Called when interstitial is loaded
            }

            @Override
            public void onInterstitialFailedToLoad() {
                Log.i("ADS", "onInterstitialFailedToLoad: ");
            }

            @Override
            public void onInterstitialShown() {
                // Called when interstitial is shown
            }

            @Override
            public void onInterstitialShowFailed() {
                Log.i("ADS", "onInterstitialShowFailed: ");
            }

            @Override
            public void onInterstitialClicked() {
                // Called when interstitial is clicked
            }

            @Override
            public void onInterstitialClosed() {

            }

            @Override
            public void onInterstitialExpired() {
                // Called when interstitial is expired
            }
        });
        Appodeal.cache((Activity) context, Appodeal.INTERSTITIAL);
    }

    @NonNull
    @Override
    public SignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.grid_item, parent, false);
        return new SignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SignViewHolder holder, int position) {

        if (isFavorite) {
            holder.mSignNameTextView.setText(translationMap
                    .getButtonName(getFavorites.get(position).getButtonText()));
        } else holder.mSignNameTextView.setText(translationMap
                .getButtonName(gameInfoList.get(position).getButtonText()));
    }

    @Override
    public int getItemCount() {
        return isFavorite ? getFavorites.size() : gameDb.gameDao().isNikol(isNikol).size();
    }

    public class SignViewHolder extends RecyclerView.ViewHolder {

        private final TextView mSignNameTextView;

        public SignViewHolder(@NonNull final View itemView) {
            super(itemView);

            mSignNameTextView = itemView.findViewById(R.id.sign_text_item_view);

            itemView.setOnClickListener(view -> {

                int soundId = isFavorite ? getFavorites.get(SignViewHolder.this.getAdapterPosition()).getButtonId()
                        : gameInfoList.get(SignViewHolder.this.getAdapterPosition()).getButtonId();

                new ClassAdapter().playGiven(context, soundId);

                int adViewCount = gameDb.gameDao().getGameInfo().get(0).getAdsCount();

                if (adViewCount % 7 == 0) {
                    Appodeal.show((Activity) context, Appodeal.INTERSTITIAL);
                }

                adViewCount++;
                gameDb.gameDao().updateAdsCount(adViewCount);
            });

            itemView.setOnLongClickListener(view -> {
                showPopupWindow(view, SignViewHolder.this.getAdapterPosition());
                return false;
            });
        }

        private void showPopupWindow(final View view, int position) {
            PopupMenu popup = new PopupMenu(context, view);
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        assert menuPopupHelper != null;
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int menuId = isFavorite ? R.menu.second_buttom_menu : R.menu.botton_menu;

            popup.getMenuInflater().inflate(menuId, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                int buttonId = isFavorite ? getFavorites.get(position).getButtonId()
                        : gameInfoList.get(position).getButtonId();

                String buttonText = isFavorite ? getFavorites.get(position).getButtonText()
                        : gameInfoList.get(position).getButtonText();

                switch (item.toString()) {

                    case "Share":

                        if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            new ClassAdapter().requestStoragePermission(context);
                        } else
                            new ClassAdapter().voiceShare(context, buttonId, buttonText);
                        break;

                    case "Add to favorites":

                        if (gameInfoList.get(position).isFavorite()) {
                            Toast.makeText(context, " Sound is already added", Toast.LENGTH_SHORT).show();
                        } else {
                            gameDb.gameDao().updateFavorite(buttonId, true);
                            Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "Remove from favorites":
                        gameDb.gameDao().updateFavorite(buttonId, false);
                        getFavorites.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getFavorites.size());

                        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                }

                return true;
            });
            popup.show();
        }
    }

}
