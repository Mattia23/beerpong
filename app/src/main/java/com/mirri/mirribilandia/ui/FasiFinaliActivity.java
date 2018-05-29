package com.mirri.mirribilandia.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;
import com.mirri.mirribilandia.util.Counter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mattia on 27/05/2018.
 */

public class FasiFinaliActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ListView listView;
    private ArrayList<FasiFinaliContent.FasiFinaliItem> arrayOfMatches;
    private Boolean wasOnPause = false;
    private ProgressBar loadingSpinner;
    private Spinner spinner;

    public interface Fasi {
        String TRENTADUESIMI = "Trentaduesimi";
        String SEDICESIMI = "Sedicesimi";
        String OTTAVI = "Ottavi";
        String QUARTI = "Quarti";
        String SEMIFINALI = "Semifinali";
        String FINALE = "Finale";

        String[] values = {TRENTADUESIMI,SEDICESIMI,OTTAVI,QUARTI,SEMIFINALI,FINALE};
        Map<String,Integer> map = new HashMap<String,Integer>(){{ put(TRENTADUESIMI,32); put(SEDICESIMI,16);
            put(OTTAVI,8); put(QUARTI,4); put(SEMIFINALI,2); put(FINALE,1);}};
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(("#1976d2")));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasi_finali);
        setupToolbar();

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Fasi.values);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        loadingSpinner =(ProgressBar)findViewById(R.id.progressBar2);
        loadingSpinner.setVisibility(View.GONE);

        listView = (ListView) findViewById(R.id.listView);
    }

    private void displayResults(String fase) {
        arrayOfMatches = new ArrayList<FasiFinaliContent.FasiFinaliItem>();
        for(FasiFinaliContent.FasiFinaliItem f : FasiFinaliContent.ITEMS) {
            if(f.turno == Fasi.map.get(fase)) {
                arrayOfMatches.add(f);
            }
        }
        MatchesAdapter adapter = new MatchesAdapter(this, arrayOfMatches);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        displayResults(Fasi.values[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
        return R.id.nav_eliminatoria;
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
        loadingSpinner.setVisibility(View.VISIBLE);
        Counter c = new Counter(null,null,null,this);
        new FasiFinaliContent(this, c);
        new GironiContent(this, c);
        new SquadreContent(this, c);
    }

    public void stopLoadingSpinner() {
        String fase = Fasi.values[spinner.getSelectedItemPosition()];
        Toast.makeText(this,fase,Toast.LENGTH_SHORT).show();
        displayResults(fase);
        loadingSpinner.setVisibility(View.GONE);
    }
}
