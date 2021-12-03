package com.example.pawonresto.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.pawonresto.BR;

import java.io.Serializable;

public class Penawaran extends BaseObservable implements Serializable {
    private int idPenawaran;
    public String imgURL;
    private String judul;
    private String deskripsi;

    public Penawaran(int idPenawaran, String judul, String deskripsi, String imgURL) {
        this.idPenawaran = idPenawaran;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.imgURL = imgURL;
    }

    public Penawaran(String judul, String deskripsi, String imgURL) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

//    @BindingAdapter("profileImage")
//    public static void loadImage(ImageView view, String imgURL) {
//        Glide.with(view.getContext())
//                .load(imgURL)
//                .into(view);
//    }


    public int getIdPenawaran() {
        return idPenawaran;
    }

    @Bindable
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
        notifyPropertyChanged(BR.judul);
    }

    @Bindable
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        notifyPropertyChanged(BR.deskripsi);
    }
}
