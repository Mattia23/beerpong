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

public class RestaurantContent implements UrlConnectionAsyncTask.UrlConnectionListener{

    public static final List<RestaurantContent.RestaurantItem> ITEMS = new ArrayList<>();
    public static final Map<String, RestaurantContent.RestaurantItem> ITEM_MAP = new HashMap<>();

    public RestaurantContent(Context context) {
        try {
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.restaurant_url)), this, context).execute(new Bundle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(RestaurantContent.RestaurantItem item) {
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
                        addItem(new RestaurantItem(attractions.getJSONObject(i).getString("id"), R.drawable.p1, attractions.getJSONObject(i).getString("nome"), attractions.getJSONObject(i).getString("descrizione"), attractions.getJSONObject(i).getString("tel")));
                    }
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
