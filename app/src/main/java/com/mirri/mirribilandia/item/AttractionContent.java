package com.mirri.mirribilandia.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mirri.mirribilandia.R;

public class AttractionContent {

    public static final List<AttractionItem> ITEMS = new ArrayList<>();
    public static final Map<String, AttractionItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new AttractionItem("1", R.drawable.p1, "ATTRAZIONE #1", "Una bellissima attrazione", 5, 120, 30, 2017, "[d98000b71678]"));
        addItem(new AttractionItem("2", R.drawable.p2, "ATTRAZIONE #2", "Una bellissima attrazione", 5, 120, 30, 2017, "cB5Hjkrvf"));
        addItem(new AttractionItem("3", R.drawable.p3, "ATTRAZIONE #3", "Una bellissima attrazione", 5, 120, 30, 2017, "cB5Hjkrvf"));
        addItem(new AttractionItem("4", R.drawable.p4, "ATTRAZIONE #4", "Una bellissima attrazione", 5, 120, 30, 2017, "cB5Hjkrvf"));
        addItem(new AttractionItem("5", R.drawable.p5, "ATTRAZIONE #5", "Una bellissima attrazione", 5, 120, 30, 2017, "cB5Hjkrvf"));
    }

    private static void addItem(AttractionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class AttractionItem {
        public final String id;
        public final int image;
        public final String name;
        public final String description;
        public final int minAge;
        public final int minHeight;
        public final int waitingTime;
        public final int buildYear;
        public final String idBeacon;

        AttractionItem(String id, int image, String name, String description, int minAge, int minHeight, int waitingTime, int buildYear, String idBeacon) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.description = description;
            this.minAge = minAge;
            this.minHeight = minHeight;
            this.waitingTime = waitingTime;
            this.buildYear = buildYear;
            this.idBeacon = idBeacon;
        }
    }
}
