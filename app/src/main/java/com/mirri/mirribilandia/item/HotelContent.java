package com.mirri.mirribilandia.item;


import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelContent {

    public static final List<HotelContent.HotelItem> ITEMS = new ArrayList<>();
    public static final Map<String, HotelContent.HotelItem> ITEM_MAP = new HashMap<>(2);

    static {
        addItem(new HotelContent.HotelItem("1", R.drawable.p1, "Hotel #1", "Un bell'hotel", "3492056874", 5));
        addItem(new HotelContent.HotelItem("2", R.drawable.p2, "Hotel #2", "Un bell'hotel", "3492056874", 5));
    }

    private static void addItem(HotelContent.HotelItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class HotelItem {
        public final String id;
        public final int image;
        public final String name;
        public final String description;
        public final String phone;
        public final int distance;

        HotelItem(String id, int image, String name, String description, String phone, int distance) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.description = description;
            this.phone = phone;
            this.distance = distance;
        }
    }
}
