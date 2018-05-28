package com.mirri.mirribilandia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;

/**
 * Created by Mattia on 25/05/2018.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GironiContent(getApplicationContext());
        new SquadreContent(getApplicationContext());

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(MainActivity.this,GironiActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }
}
