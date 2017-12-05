package com.mirri.mirribilandia.item;


import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelContent {

    /**
     * An array of sample items.
     */
    public static final List<HotelContent.HotelItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, HotelContent.HotelItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new HotelContent.HotelItem("1", R.drawable.p1, "Hotel #1", "Steve Jobs", "Focusing is about saying No."));
        addItem(new HotelContent.HotelItem("2", R.drawable.p2, "Hotel #2", "Napoleon Hill","A quitter never wins and a winner never quits."));
    }

    private static void addItem(HotelContent.HotelItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class HotelItem {
        public final String id;
        public final int photoId;
        public final String name;
        public final String description;
        public final String phone;

        HotelItem(String id, int photoId, String name, String description, String phone) {
            this.id = id;
            this.photoId = photoId;
            this.name = name;
            this.description = description;
            this.phone = phone;
        }
    }
}
