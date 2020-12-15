package com.example.hello.appnav;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.appnav.config.Utils;
import com.example.hello.appnav.viewAdapter.ButtonItemAdapter;

public class SecondFragment extends Fragment {
    Context context;
    Boolean isNikol;

    private RecyclerView mRecyclerView;
    private ButtonItemAdapter buttonItemAdapter;

    public SecondFragment(Boolean isNikol) {
        this.isNikol = isNikol;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_list, container, false);
        context = rootView.getContext();

        mRecyclerView = rootView.findViewById(R.id.grid_recycler_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils viewModel = new ViewModelProvider(this).get(Utils.class);
        viewModel.getGridData().observe(getViewLifecycleOwner(), gridItems -> {
            buttonItemAdapter = new ButtonItemAdapter(requireContext(), isNikol, true);
            mRecyclerView.setAdapter(buttonItemAdapter);
        });
    }
}