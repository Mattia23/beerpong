package com.mirri.mirribilandia.ui;

import com.mirri.mirribilandia.ui.attraction.AttractionListFragment;
import com.mirri.mirribilandia.ui.base.BaseActivity;

public class HotelActivity extends BaseActivity implements AttractionListFragment.Callback {

    @Override
    public void onItemSelected(String id) {

    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
