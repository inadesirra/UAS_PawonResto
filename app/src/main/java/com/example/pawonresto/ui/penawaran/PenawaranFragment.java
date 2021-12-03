package com.example.pawonresto.ui.penawaran;

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
import com.example.pawonresto.adapter.PenawaranRecycleViewAdapter;
import com.example.pawonresto.databinding.FragmentPenawaranBinding;
import com.example.pawonresto.model.Penawaran;

import java.util.ArrayList;
import java.util.List;

public class PenawaranFragment extends Fragment {

    PenawaranRecycleViewAdapter adapter;
    List<Penawaran> listPenawaran = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;

    FragmentPenawaranBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_penawaran, container, false);
        View view = binding.getRoot();

        listPenawaran = new DataPenawaran().Penawaran;

        adapter = new PenawaranRecycleViewAdapter(inflater.getContext(), listPenawaran);
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.penawaranRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        binding.penawaranRv.setHasFixedSize(true);
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