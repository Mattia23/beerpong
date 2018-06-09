package com.mirri.mirribilandia.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.gironi.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mattia on 09/06/2018.
 */

public class TerzeActivity extends BaseActivity {

    private ListView listView;
    private List<Team> arrayOfThirds;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(("#1976d2")));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terze);
        setupToolbar();

        listView = (ListView) findViewById(R.id.listViewTerze);
        displayTable();

        ImageView refresh = findViewById(R.id.refreshButton);
        refresh.setVisibility(View.INVISIBLE);
    }

    private void displayTable() {

        TerzeContainer container = MainActivity.getThirdContainer();
        arrayOfThirds = container.getThirdListTeams();

        TerzeAdapter adapter = new TerzeAdapter(this, arrayOfThirds);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_terze;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
