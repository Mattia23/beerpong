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

    public RestaurantContent(Context context, String attractionId) {
        ITEMS.clear();
        ITEM_MAP.clear();
        try {
            Bundle data = new Bundle();
            data.putString("attrazione", attractionId);
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.restaurant_url_by_attraction)), this, context).execute(data);
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
                    final JSONArray restaurant = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < restaurant.length(); i++) {
                        addItem(new RestaurantItem(restaurant.getJSONObject(i).getString("id"), restaurant.getJSONObject(i).getString("immagine"), restaurant.getJSONObject(i).getString("nome"), restaurant.getJSONObject(i).getString("descrizione"), restaurant.getJSONObject(i).getString("tel"), restaurant.getJSONObject(i).getInt("distanza")));
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
        public final String image;
        public final String name;
        public final String description;
        public final String phone;
        public final int distance;

        RestaurantItem(String id, String image, String name, String description, String phone, int distance) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.description = description;
            this.phone = phone;
            this.distance = distance;
        }
    }
}
