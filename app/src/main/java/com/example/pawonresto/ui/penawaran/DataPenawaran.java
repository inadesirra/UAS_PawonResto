package com.example.pawonresto.ui.penawaran;

import com.example.pawonresto.model.Penawaran;

import java.util.ArrayList;

public class DataPenawaran {
    public ArrayList<Penawaran> Penawaran;

    public DataPenawaran() {
        Penawaran = new ArrayList<>();
        Penawaran.add(PENAWARAN1);
        Penawaran.add(PENAWARAN2);
        Penawaran.add(PENAWARAN3);
        Penawaran.add(PENAWARAN4);
        Penawaran.add(PENAWARAN5);
    }

    public static final Penawaran PENAWARAN1 = new Penawaran("https://i.pinimg.com/236x/6e/63/86/6e6386b4531136da4fa93ce08136ca41.jpg", "Penawaran 01",
            "Hari ini beli 2 gratis 1");
    public static final Penawaran PENAWARAN2 = new Penawaran("https://s3.dealjava.com/content/thumb_small/73c3630cc248c3db2513f45c7338a250.jpg", "Penawaran 02",
            "All u can eat Rp.100.000,00");
    public static final Penawaran PENAWARAN3 = new Penawaran("https://assets.grab.com/wp-content/uploads/sites/9/2019/09/09103332/Blog_1440x700-Flash-Deal.jpg", "Penawaran 03",
            "Makan di tempat mendapat discount 50%");
    public static final Penawaran PENAWARAN4 = new Penawaran("https://www.garnews.com/wp-content/uploads/2021/10/17532-5da7bde00d8230196c0c9a93-660x330.jpg", "Penawaran 04",
            "Discount 30% bayar menggunakan OVO");
    public static final Penawaran PENAWARAN5 = new Penawaran("https://lelogama.go-jek.com/post_thumbnail/cara-pesan-gomart.jpg", "Penawaran 05",
            "Lebih hemat pesan melalui aplikasi kami");
}
