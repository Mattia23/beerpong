package com.mirri.mirribilandia.item;


import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoContent {
    public static final List<PhotoContent.PhotoItem> ITEMS = new ArrayList<>();
    public static final Map<String, PhotoContent.PhotoItem> ITEM_MAP = new HashMap<>(2);

    static {
        addItem(new PhotoContent.PhotoItem("1", R.drawable.p1, "username", "nome attrazione", "25/09/17"));
        addItem(new PhotoContent.PhotoItem("2", R.drawable.p2, "username", "nome attrazione", "25/09/17"));
    }

    private static void addItem(PhotoContent.PhotoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class PhotoItem {
        public final String id;
        public final int image;
        public final String username;
        public final String attraction;
        public final String date;

        PhotoItem(String id, int image, String username, String attraction, String date) {
            this.id = id;
            this.image = image;
            this.username = username;
            this.attraction = attraction;
            this.date = date;
        }
    }
}