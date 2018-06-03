package com.mirri.mirribilandia.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.util.Counter;

/**
 * Created by Mattia on 27/05/2018.
 */

public class SquadreActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private TextView mem1,mem2;
    private Boolean wasOnPause = false;
    private ImageButton button;

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(("#1976d2")));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squadre);
        setupToolbar();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        mem1 = (TextView) findViewById(R.id.membro1);
        mem2 = (TextView) findViewById(R.id.membro2);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, SquadreContent.ARRAY_SQUADRE.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        button = (ImageButton) findViewById(R.id.refreshButton);
        button.setOnClickListener(view -> refreshContents());
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        mem1.setText(SquadreContent.ITEMS.get(position).membro1);
        mem2.setText(SquadreContent.ITEMS.get(position).membro2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
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
        return R.id.nav_squadre;
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

    @Override
    protected void onPause() {
        super.onPause();
        wasOnPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasOnPause) {
            refreshContents();
            wasOnPause = false;
        }
    }

    private void refreshContents() {
        progressDialog.show();
        Counter c = new Counter(null,null,null,this,null);
        new FasiFinaliContent(this, c);
        new GironiContent(this, c);
        new SquadreContent(this, c);
    }

    public void stopLoadingSpinner() {
        progressDialog.dismiss();
    }
}
