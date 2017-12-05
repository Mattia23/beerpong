package com.mirri.mirribilandia.item;


import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantContent {
    /**
     * An array of sample items.
     */
    public static final List<RestaurantContent.RestaurantItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, RestaurantContent.RestaurantItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new RestaurantContent.RestaurantItem("1", R.drawable.p1, "CIAO #1", "Steve Jobs", ""));
        addItem(new RestaurantContent.RestaurantItem("3", R.drawable.p3, "Quote #3", "Pablo Picaso", ""));
        addItem(new RestaurantContent.RestaurantItem("4", R.drawable.p4, "Quote #4", "Napoleon Hill", ""));
        addItem(new RestaurantContent.RestaurantItem("2", R.drawable.p2, "Quote #2", "Napoleon Hill", ""));
        addItem(new RestaurantContent.RestaurantItem("5", R.drawable.p5, "Quote #5", "Steve Jobs", ""));
        addItem(new RestaurantContent.RestaurantItem("5", R.drawable.p5, "Quote #5", "Steve Jobs", ""));
    }

    private static void addItem(RestaurantContent.RestaurantItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class RestaurantItem {
        public final String id;
        public final int photoId;
        public final String name;
        public final String description;
        public final String phone;

        RestaurantItem(String id, int photoId, String name, String description, String phone) {
            this.id = id;
            this.photoId = photoId;
            this.name = name;
            this.description = description;
            this.phone = phone;
        }
    }
}
