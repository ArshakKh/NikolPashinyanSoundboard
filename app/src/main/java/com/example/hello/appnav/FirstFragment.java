package com.example.hello.appnav;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.appnav.adapterDb.GameDb;
import com.example.hello.appnav.config.Utils;
import com.example.hello.appnav.viewAdapter.ButtonItemAdapter;

public class FirstFragment extends Fragment {
    View rootView;
    private RecyclerView mRecyclerView;
    private ButtonItemAdapter buttonItemAdapter;
    private Boolean isFirst;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Use sound as");
        menu.add(0, v.getId(), 0, "Add to favorites")
                .setIcon(R.drawable.star);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.grid_recycler_view);

        GameDb gameDb = WelcomeActivity.gameDb;
        isFirst = gameDb.gameDao().getGameInfo().get(0).isIs1();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils viewModel = new ViewModelProvider(this).get(Utils.class);
        viewModel.getGridData().observe(getViewLifecycleOwner(), gridItems -> {
            buttonItemAdapter = new ButtonItemAdapter(requireContext(), isFirst, false);
            mRecyclerView.setAdapter(buttonItemAdapter);
        });
    }


}
