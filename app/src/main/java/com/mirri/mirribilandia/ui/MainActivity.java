package com.mirri.mirribilandia.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.FasiFinaliContent;
import com.mirri.mirribilandia.item.GironiContent;
import com.mirri.mirribilandia.item.SquadreContent;
import com.mirri.mirribilandia.util.Counter;

import static com.mirri.mirribilandia.util.Utilities.MY_PREFS_NAME;
import static com.mirri.mirribilandia.util.Utilities.RESTART;

/**
 * Created by Mattia on 25/05/2018.
 */

public class MainActivity extends Activity {

    private boolean canGo = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Counter c = new Counter(this,null,null,null);
        new GironiContent(getApplicationContext(),c);
        new SquadreContent(getApplicationContext(),c);
        new FasiFinaliContent(getApplicationContext(),c);

        ImageView imageView = findViewById(R.id.imageView3);

        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000);
        anim.setRepeatCount(1);
        anim.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(anim);

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    if(canGo) {
                        startGironi();
                    } else {
                        canGo = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    private void startGironi() {
        canGo = false;
        Intent intent = new Intent(MainActivity.this, GironiActivity.class);
        startActivity(intent);
        finish();
    }

    public void stopLoadingSpinner() {
        if(canGo) {
            startGironi();
        } else {
            canGo = true;
        }
    }

}
