package com.example.pawonresto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawonresto.R;
import com.example.pawonresto.databinding.ItemMenuBinding;
import com.example.pawonresto.model.Menu;

import java.util.List;

public class MenuRecycleViewAdapter extends RecyclerView.Adapter<MenuRecycleViewAdapter.MenuViewHolder> {
    private Context context;
    private List<Menu> menuList;

    public MenuRecycleViewAdapter(Context context, List<Menu> result) {
        this.context = context;
        this.menuList = result;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemMenuBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_menu,parent,
                false);

        return new MenuViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.myBinding(menu);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder{
        ItemMenuBinding binding;

        public MenuViewHolder(@NonNull ItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void myBinding(Menu menu) {
            binding.setMenu(menu);
            binding.executePendingBindings();
        }
    }
}

