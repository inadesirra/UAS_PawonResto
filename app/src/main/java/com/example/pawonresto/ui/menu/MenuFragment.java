package com.example.pawonresto.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawonresto.R;
import com.example.pawonresto.adapter.MenuRecycleViewAdapter;
import com.example.pawonresto.databinding.FragmentMenuBinding;
import com.example.pawonresto.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    MenuRecycleViewAdapter adapter;
    List<Menu> listMenu = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;

    FragmentMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        View view = binding.getRoot();

        listMenu = new DataMenu().Menu;

        adapter = new MenuRecycleViewAdapter(inflater.getContext(), listMenu);
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.menuRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        binding.menuRv.setHasFixedSize(true);
        binding.setData(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
    }
}