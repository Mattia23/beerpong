package com.mirri.mirribilandia.item;

import android.content.Context;
import android.os.Bundle;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.ui.UrlConnectionAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventContent implements UrlConnectionAsyncTask.UrlConnectionListener {
    public static final List<EventContent.EventItem> ITEMS = new ArrayList<>();
    public static final Map<String, EventContent.EventItem> ITEM_MAP = new HashMap<>();

    public EventContent(Context context){
        try {
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.photo_url)), this, context).execute(new Bundle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(EventContent.EventItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            try {
                final int code = response.getInt("code");
                if(code == 2) {
                    final JSONArray attractions = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < attractions.length(); i++) {
                        addItem(new EventItem(attractions.getJSONObject(i).getString("id"), attractions.getJSONObject(i).getString("nome"), attractions.getJSONObject(i).getString("descrizione"), attractions.getJSONObject(i).getString("attrazione"), attractions.getJSONObject(i).getString("data")));
                    }
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class EventItem {
        public final String id;
        public final String name;
        public final String description;
        public final String attraction;
        public final String date;

        EventItem(String id, String name, String description, String attraction, String date) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.attraction = attraction;
            this.date = date;
        }
    }
}
