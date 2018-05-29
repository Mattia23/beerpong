package com.mirri.mirribilandia.item;

import android.content.Context;
import android.os.Bundle;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.ui.UrlConnectionAsyncTask;
import com.mirri.mirribilandia.util.Counter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mattia on 23/05/2018.
 */

public class FasiFinaliContent implements UrlConnectionAsyncTask.UrlConnectionListener {

    public static final List<FasiFinaliContent.FasiFinaliItem> ITEMS = new ArrayList<>();
    private Counter counter;

    public FasiFinaliContent(Context context, Counter c) {
        this.counter = c;
        ITEMS.clear();
        try {
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.eliminatoria_url)), this, context).execute(new Bundle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(FasiFinaliContent.FasiFinaliItem item) {
        ITEMS.add(item);
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            try {
                final int code = response.getInt("code");
                if(code == 2) {
                    final JSONArray partita = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < partita.length(); i++) {
                        addItem(new FasiFinaliItem(partita.getJSONObject(i).getInt("id"),
                                partita.getJSONObject(i).getString("sq1"),
                                partita.getJSONObject(i).getString("sq2"),
                                partita.getJSONObject(i).getInt("goal1"),
                                partita.getJSONObject(i).getInt("goal2"),
                                partita.getJSONObject(i).getInt("turno")));
                    }
                    counter.increment();
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class FasiFinaliItem {
        public final int id;
        public final String sq1;
        public final String sq2;
        public final int goal1;
        public final int goal2;
        public final int turno;

        FasiFinaliItem(int id, String sq1, String sq2, int goal1, int goal2, int turno) {
            this.id = id;
            this.sq1 = sq1;
            this.sq2 = sq2;
            this.goal1 = goal1;
            this.goal2 = goal2;
            this.turno = turno;
        }
    }
}
