package com.mirri.mirribilandia.item;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

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

/**
 * Created by Mattia on 23/05/2018.
 */

public class GironiContent implements UrlConnectionAsyncTask.UrlConnectionListener{

        public static final List<PartiteItem> ITEMS = new ArrayList<>();
        public static final Map<String, PartiteItem> ITEM_MAP = new HashMap<>();
        public static final List<Integer> GIRONI_ITEMS = new ArrayList<>();

        private Context context;

        public GironiContent(Context context){
            this.context = context;
            try {
                new UrlConnectionAsyncTask(new URL(context.getString(R.string.gironi_url)), this, context).execute(new Bundle());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        private static void addItem(PartiteItem item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.id, item);
        }

        @Override
        public void handleResponse(JSONObject response, Bundle extra) {
            if(response.length() != 0) {
                try {
                    final int code = response.getInt("code");
                    if(code == 2) {
                        int precGirone = -1;
                        final JSONArray partita = response.getJSONObject("extra").getJSONArray("data");
                        for(int i = 0; i < partita.length(); i++) {
                            int actualGirone = partita.getJSONObject(i).getInt("girone");
                            addItem(new PartiteItem(   partita.getJSONObject(i).getString("id"),
                                                                    partita.getJSONObject(i).getString("sq1"),
                                                                    partita.getJSONObject(i).getString("sq2"),
                                                                    partita.getJSONObject(i).getInt("goal1"),
                                                                    partita.getJSONObject(i).getInt("goal2"),
                                                                    actualGirone));

                            if(precGirone != actualGirone){
                                GIRONI_ITEMS.add(actualGirone);
                                precGirone = actualGirone;
                            }
                        }
                    } else {
                        Toast.makeText(this.context, "Errore sconosciuto 1", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this.context, "Errore sconosciuto 2", Toast.LENGTH_LONG).show();
                }
            }
        }

        public static class PartiteItem {
            public final String id;
            public final String sq1;
            public final String sq2;
            public final int goal1;
            public final int goal2;
            public final int girone;


            PartiteItem(String id, String sq1, String sq2, int goal1, int goal2, int girone) {
                this.id = id;
                this.sq1 = sq1;
                this.sq2 = sq2;
                this.goal1 = goal1;
                this.goal2 = goal2;
                this.girone = girone;
            }
        }
    }