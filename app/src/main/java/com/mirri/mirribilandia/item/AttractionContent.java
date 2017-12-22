package com.mirri.mirribilandia.item;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.ui.UrlConnectionAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AttractionContent implements UrlConnectionAsyncTask.UrlConnectionListener{

    public static final List<AttractionItem> ITEMS = new ArrayList<>();
    public static final Map<String, AttractionItem> ITEM_MAP = new HashMap<>();

    public AttractionContent(Context context){
        try {
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.attraction_url)), this, context).execute(new Bundle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(AttractionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            try {
                final int code = response.getInt("code");
                if(code == 2) {
                    final JSONArray attraction = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < attraction.length(); i++) {
                        addItem(new AttractionItem(attraction.getJSONObject(i).getString("id"), attraction.getJSONObject(i).getString("immagine"), attraction.getJSONObject(i).getString("nome"), attraction.getJSONObject(i).getString("descrizione"), attraction.getJSONObject(i).getInt("eta_min"), attraction.getJSONObject(i).getInt("alt_min"), attraction.getJSONObject(i).getInt("tempo_attesa"), attraction.getJSONObject(i).getInt("anno_costruzione"), attraction.getJSONObject(i).getString("beacon")));
                    }
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class AttractionItem {
        public final String id;
        public final String image;
        public final String name;
        public final String description;
        public final int minAge;
        public final int minHeight;
        public final int waitingTime;
        public final int buildYear;
        public final String idBeacon;

        AttractionItem(String id, String image, String name, String description, int minAge, int minHeight, int waitingTime, int buildYear, String idBeacon) {
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
