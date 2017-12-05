package com.mirri.mirribilandia.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mirri.mirribilandia.R;

public class AttractionContent {

    /**
     * An array of sample items.
     */
    public static final List<AttractionItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample items. Key: sample ID; Value: Item.
     */
    public static final Map<String, AttractionItem> ITEM_MAP = new HashMap<>(5);

    static {
        addItem(new AttractionItem("1", R.drawable.p1, "ciaosuper #1", "Steve Jobs", "Focusing is about saying No."));
        addItem(new AttractionItem("2", R.drawable.p2, "Quote #2", "Napoleon Hill","A quitter never wins and a winner never quits."));
        addItem(new AttractionItem("3", R.drawable.p3, "Quote #3", "Pablo Picaso", "Action is the foundational key to all success."));
        addItem(new AttractionItem("4", R.drawable.p4, "Quote #4", "Napoleon Hill","Our only limitations are those we set up in our own minds."));
        addItem(new AttractionItem("5", R.drawable.p5, "Quote #5", "Steve Jobs","Deciding what not do do is as important as deciding what to do."));
    }

    private static void addItem(AttractionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class AttractionItem {
        public final String id;
        public final int photoId;
        public final String title;
        public final String author;
        public final String content;

        AttractionItem(String id, int photoId, String title, String author, String content) {
            this.id = id;
            this.photoId = photoId;
            this.title = title;
            this.author = author;
            this.content = content;
        }
    }
}
