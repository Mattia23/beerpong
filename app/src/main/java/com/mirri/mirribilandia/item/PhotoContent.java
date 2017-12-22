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
                    final JSONArray photo = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < photo.length(); i++) {
                        addItem(new PhotoItem(photo.getJSONObject(i).getString("id"), photo.getJSONObject(i).getString("immagine"), photo.getJSONObject(i).getString("utente"), photo.getJSONObject(i).getString("attrazione"), photo.getJSONObject(i).getString("data")));
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
        public final String image;
        public final String username;
        public final String attraction;
        public final String date;

        PhotoItem(String id, String image, String username, String attraction, String date) {
            this.id = id;
            this.image = image;
            this.username = username;
            this.attraction = attraction;
            this.date = date;
        }
    }
}