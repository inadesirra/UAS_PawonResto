package com.example.pawonresto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawonresto.R;
import com.example.pawonresto.databinding.ItemPenawaranBinding;
import com.example.pawonresto.model.Penawaran;

import java.util.List;

public class PenawaranRecycleViewAdapter extends RecyclerView.Adapter<PenawaranRecycleViewAdapter.PenawaranViewHolder> {
    private Context context;
    private List<Penawaran> penawaranList;

    public PenawaranRecycleViewAdapter(Context context, List<Penawaran> result) {
        this.context = context;
        this.penawaranList = result;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PenawaranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemPenawaranBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_penawaran,
                parent, false);

        return new PenawaranViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PenawaranViewHolder holder, int position) {
        Penawaran penawaran = penawaranList.get(position);
        holder.myBinding(penawaran);
    }

    @Override
    public int getItemCount() {
        return penawaranList.size();
    }


    public class PenawaranViewHolder extends RecyclerView.ViewHolder{
        ItemPenawaranBinding binding;

        public PenawaranViewHolder(@NonNull ItemPenawaranBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void myBinding(Penawaran penawaran) {
            binding.setPenawaran(penawaran);
            binding.executePendingBindings();
        }
    }
}
