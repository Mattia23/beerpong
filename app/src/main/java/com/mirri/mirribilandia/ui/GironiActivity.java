package com.mirri.mirribilandia.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.ui.gironi.GironiDetailActivity;
import com.mirri.mirribilandia.ui.gironi.GironiDetailFragment;
import com.mirri.mirribilandia.ui.gironi.GironiListFragment;
import com.mirri.mirribilandia.util.Counter;
import com.mirri.mirribilandia.util.LogUtil;

import java.time.Duration;

import static com.mirri.mirribilandia.util.Utilities.MY_PREFS_NAME;
import static com.mirri.mirribilandia.util.Utilities.RESTART;

/**
 * Created by Mattia on 23/05/2018.
 */

public class GironiActivity extends BaseActivity implements GironiListFragment.Callback {
    /**
     * Whether or not the activity is running on a device with a large screen
     */
    protected boolean twoPaneMode;
    private Boolean wasOnPause = false;
    private Boolean wasOnDetailFragment = false;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gironi_list);
        setupToolbar();

        if (isTwoPaneLayoutUsed()) {
            twoPaneMode = true;
            enableActiveItemState();
        }

        if (savedInstanceState == null && twoPaneMode) {
            setupDetailFragment();
        }

        button = (ImageButton) findViewById(R.id.refreshButton);
        button.setOnClickListener(view -> refreshContents());
    }

    @Override
    public void onItemSelected(String id) {
        if (twoPaneMode) {
            // Show the quote detail information by replacing the DetailFragment via transaction.
            GironiDetailFragment fragment = GironiDetailFragment.newInstance(id);
            getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
        } else {
            // Start the detail activity in single pane mode.
            wasOnDetailFragment = true;
            Intent detailIntent = new Intent(this, GironiDetailActivity.class);
            detailIntent.putExtra(GironiDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupDetailFragment() {
        GironiDetailFragment fragment =  GironiDetailFragment.newInstance(GironiContent.ITEMS.get(0).id);
        getFragmentManager().beginTransaction().replace(R.id.article_detail_container, fragment).commit();
    }

    private void enableActiveItemState() {
        GironiListFragment fragmentById = (GironiListFragment) getFragmentManager().findFragmentById(R.id.article_list);
        fragmentById.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

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
        return R.id.nav_gironi;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasOnPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasOnPause && !wasOnDetailFragment) {
            refreshContents();
            wasOnPause = false;
        } else if (wasOnPause && wasOnDetailFragment) {
            wasOnPause = false;
            wasOnDetailFragment = false;
        }
    }

    private void refreshContents() {
        progressDialog.show();
        Counter c = new Counter(null,this,null,null, null);
        new FasiFinaliContent(this, c);
        new GironiContent(this, c);
        new SquadreContent(this, c);
    }

    public void stopLoadingSpinner() {
        progressDialog.dismiss();
    }

}
