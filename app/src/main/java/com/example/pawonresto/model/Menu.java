package com.example.pawonresto.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.pawonresto.BR;

public class Menu extends BaseObservable {
    public String imgURL;
    private String nama_makanan;
    private double harga_makanan;

    public Menu(String imgURL, String nama_makanan, double harga_makanan) {
        this.imgURL = imgURL;
        this.nama_makanan = nama_makanan;
        this.harga_makanan = harga_makanan;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imgURl) {
        Glide.with(view.getContext())
                .load(imgURl)
                .into(view);
    }

    @Bindable
    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
        notifyPropertyChanged(BR.nama_makanan);
    }

    @Bindable
    public Double getHarga_makanan() {
        return harga_makanan;
    }

    public void setHarga_makanan(Double harga_makanan) {
        this.harga_makanan = harga_makanan;
        notifyPropertyChanged(BR.harga_makanan);
    }
}