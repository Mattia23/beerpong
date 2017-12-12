package com.mirri.mirribilandia.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ListView;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.beacon.BeaconService;
import com.mirri.mirribilandia.item.AttractionContent;
import com.mirri.mirribilandia.item.PhotoContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.attraction.AttractionDetailActivity;
import com.mirri.mirribilandia.ui.attraction.AttractionDetailFragment;
import com.mirri.mirribilandia.ui.attraction.AttractionListFragment;
import com.mirri.mirribilandia.util.LogUtil;

public class AttractionActivity extends BaseActivity implements AttractionListFragment.Callback {
    /**
     * Whether or not the activity is running on a device with a large screen
     */
    protected boolean twoPaneMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_list);

        setupToolbar();

        if (isTwoPaneLayoutUsed()) {
            twoPaneMode = true;
            LogUtil.logD("TEST","TWO POANE TASDFES");
            enableActiveItemState();
        }

        if (savedInstanceState == null && twoPaneMode) {
            setupDetailFragment();
        }

        if(!isMyServiceRunning(BeaconService.class)){
            startService(new Intent(this, BeaconService.class));
        }
    }

    /**
     * Called when an item has been selected
     *
     * @param id the selected quote ID
     */
    @Override
    public void onItemSelected(String id) {
        if (twoPaneMode) {
            // Show the quote detail information by replacing the DetailFragment via transaction.
            AttractionDetailFragment fragment = AttractionDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
        } else {
            // Start the detail activity in single pane mode.
            Intent detailIntent = new Intent(this, AttractionDetailActivity.class);
            detailIntent.putExtra(AttractionDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDetailFragment() {
        AttractionDetailFragment fragment =  AttractionDetailFragment.newInstance(AttractionContent.ITEMS.get(0).id);
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
    }

    /**
     * Enables the functionality that selected items are automatically highlighted.
     */
    private void enableActiveItemState() {
        AttractionListFragment fragmentById = (AttractionListFragment) getFragmentManager().findFragmentById(R.id.article_list);
        fragmentById.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    /**
     * Is the container present? If so, we are using the two-pane layout.
     *
     * @return true if the two pane layout is used.
     */
    private boolean isTwoPaneLayoutUsed() {
        return findViewById(R.id.article_detail_container) != null;
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
        return R.id.nav_attraction;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
