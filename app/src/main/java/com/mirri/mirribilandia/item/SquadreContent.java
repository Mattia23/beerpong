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
import java.util.List;

/**
 * Created by Mattia on 23/05/2018.
 */

public class SquadreContent implements UrlConnectionAsyncTask.UrlConnectionListener {

    public static final List<SquadreContent.SquadreItem> ITEMS = new ArrayList<>();
    public static final List<String> ARRAY_SQUADRE = new ArrayList<>();

    public SquadreContent(Context context) {
        try {
            new UrlConnectionAsyncTask(new URL(context.getString(R.string.squadre_url)), this, context).execute(new Bundle());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(SquadreContent.SquadreItem item) {
        ITEMS.add(item);
        ARRAY_SQUADRE.add(item.nome);
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            try {
                final int code = response.getInt("code");
                if(code == 2) {
                    final JSONArray squadra = response.getJSONObject("extra").getJSONArray("data");
                    for(int i = 0; i < squadra.length(); i++) {
                        addItem(new SquadreItem(squadra.getJSONObject(i).getString("id"), squadra.getJSONObject(i).getString("nome"), squadra.getJSONObject(i).getInt("girone"),squadra.getJSONObject(i).getString("membro1"), squadra.getJSONObject(i).getString("membro2")));
                    }
                } else {
                    //Toast.makeText(context, "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SquadreItem {
        public final String id;
        public final String nome;
        public final String membro1;
        public final String membro2;
        public final int girone;

        SquadreItem(String id, String nome, int girone, String membro1, String membro2) {
            this.id = id;
            this.nome = nome;
            this.membro1 = membro1;
            this.membro2 = membro2;
            this.girone = girone;
        }
    }
}
