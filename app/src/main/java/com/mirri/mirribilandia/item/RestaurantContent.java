package com.mirri.mirribilandia.item;


import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantContent {

    public static final List<RestaurantContent.RestaurantItem> ITEMS = new ArrayList<>();
    public static final Map<String, RestaurantContent.RestaurantItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new RestaurantContent.RestaurantItem("1", R.drawable.p1, "BAR #1", "Un bel bar", "3495025478"));
        addItem(new RestaurantContent.RestaurantItem("3", R.drawable.p3, "RISTORANTE #3", "Un bel ristorante", "3025698745"));
        addItem(new RestaurantContent.RestaurantItem("4", R.drawable.p4, "BAR #4", "Un bel bar", "365874105"));
    }

    private static void addItem(RestaurantContent.RestaurantItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class RestaurantItem {
        public final String id;
        public final int image;
        public final String name;
        public final String description;
        public final String phone;

        RestaurantItem(String id, int image, String name, String description, String phone) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.description = description;
            this.phone = phone;
        }
    }
}
