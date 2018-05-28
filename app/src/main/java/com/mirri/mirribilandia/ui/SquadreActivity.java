package com.mirri.mirribilandia.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.ui.base.BaseActivity;

/**
 * Created by Mattia on 27/05/2018.
 */

public class SquadreActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squadre);


        TextView textView = (TextView) findViewById(R.id.textView);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, SquadreContent.ARRAY_SQUADRE.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),(String)(SquadreContent.ARRAY_SQUADRE.toArray())[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_squadre;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }
}
