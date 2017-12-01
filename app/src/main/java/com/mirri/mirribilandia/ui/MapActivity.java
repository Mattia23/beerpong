package com.mirri.mirribilandia.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.ui.base.BaseActivity;

public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setupToolbar();
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
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
        return R.id.nav_map;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

}
