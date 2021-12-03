package com.example.pawonresto.ui.menu;

import com.example.pawonresto.model.Menu;

import java.util.ArrayList;

public class DataMenu {
    public ArrayList<Menu> Menu;

    public DataMenu() {
        Menu = new ArrayList<>();
        Menu.add(MENU1);
        Menu.add(MENU2);
        Menu.add(MENU3);
        Menu.add(MENU4);
        Menu.add(MENU5);
        Menu.add(MENU6);
        Menu.add(MENU7);
        Menu.add(MENU8);
        Menu.add(MENU9);
        Menu.add(MENU10);
    }

    public static final Menu MENU1 = new Menu("https://www.resepistimewa.com/wp-content/uploads/soto-daging-sapi.jpg", "Soto Daging Sapi",25000
            );
    public static final Menu MENU2 = new Menu("https://img-global.cpcdn.com/recipes/a9b2ccc185a628c7/751x532cq70/krengsengan-kambing-foto-resep-utama.jpg", "Krengsengan Iga Kambing",30000
            );
    public static final Menu MENU3 = new Menu("https://www.masakapahariini.com/wp-content/uploads/2021/10/Nasi-Pecel-Madiun-780x440.jpg", "Pecel Madiun",20000
            );
    public static final Menu MENU4 = new Menu("https://s0.bukalapak.com/uploads/content_attachment/f977148d24e30b743df3f2b5/w-740/foto_thumbnail_ayam_penyet.jpg", "Ayam Penyet",30000
            );
    public static final Menu MENU5 = new Menu("https://www.resepistimewa.com/wp-content/uploads/resep-nasi-uduk-rice-cooker.jpg", "Nasi Uduk",30000
            );

    public static final Menu MENU6 = new Menu("https://cdns.klimg.com/dream.co.id/resized/640x320/news/2019/12/16/125112/664xauto-minuman-tradisional-indonesia-bakal-hits-di-2020-191216q.jpg", "Cendol Dawet",10000
    );
    public static final Menu MENU7 = new Menu("https://assets.promediateknologi.com/crop/0x0:0x0/x/photo/2021/08/21/3935118040.jpg", "Ronde",30000
    );
    public static final Menu MENU8 = new Menu("https://static.tokopedia.net/blog/wp-content/uploads/2019/08/minuman-khas-Indonesia-10-Tribunnews.jpg", "Wedang Jahe",10000
    );
    public static final Menu MENU9 = new Menu("https://ds393qgzrxwzn.cloudfront.net/resize/m500x500/cat1/img/images/0/0ME0nnnvx2.jpg", "Bubur Kacang Hijau",30000
    );
    public static final Menu MENU10 = new Menu("https://nusadaily.com/wp-content/uploads/2020/08/sop-buah-doyan-resep.jpg", "Es Buah",17000
    );
}