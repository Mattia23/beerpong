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

public class PhotoContent implements UrlConnectionAsyncTask.UrlConnectionListener{
    public static final List<PhotoContent.PhotoItem> ITEMS = new ArrayList<>();
    public static final Map<String, PhotoContent.PhotoItem> ITEM_MAP = new HashMap<>();
    //TODO ogni volta che si riavvia l'app occorre cancellare le hash map e arraylist
    public PhotoContent(Context context, String username, String password){
        try {
            Bundle data = new Bundle();
            data.putString("username", username);
            data.putString("password", password);
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.photo_url)), this, context).execute(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(PhotoContent.PhotoItem item) {
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
                        addItem(new PhotoItem(attractions.getJSONObject(i).getString("id"), R.drawable.p1, attractions.getJSONObject(i).getString("utente"), attractions.getJSONObject(i).getString("attrazione"), attractions.getJSONObject(i).getString("data")));
                    }
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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