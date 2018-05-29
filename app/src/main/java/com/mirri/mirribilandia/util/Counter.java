package com.mirri.mirribilandia.util;

import com.mirri.mirribilandia.ui.FasiFinaliActivity;
import com.mirri.mirribilandia.ui.GironiActivity;
import com.mirri.mirribilandia.ui.MainActivity;
import com.mirri.mirribilandia.ui.SquadreActivity;

/**
 * Created by Mattia on 29/05/2018.
 */

public class Counter {

    private int counter;
    private MainActivity mainActivity;
    private GironiActivity gironiActivity;
    private SquadreActivity squadreActivity;
    private FasiFinaliActivity fasiFinaliActivity;

    public Counter (MainActivity m, GironiActivity g, SquadreActivity s, FasiFinaliActivity f) {
        this.counter = 0;
        this.mainActivity = m;
        this.gironiActivity = g;
        this.squadreActivity = s;
        this.fasiFinaliActivity = f;
    }

    public void increment() {
        counter++;
        if(counter > 2) {
            if(mainActivity == null && gironiActivity == null && squadreActivity == null) {
                fasiFinaliActivity.stopLoadingSpinner();
            } else if(mainActivity == null && gironiActivity == null && fasiFinaliActivity == null) {
                squadreActivity.stopLoadingSpinner();
            } else if(mainActivity == null && squadreActivity == null && fasiFinaliActivity == null) {
                //gironiActivity.stopLoadingSpinner();
            } else if(gironiActivity == null && squadreActivity == null && fasiFinaliActivity == null) {
                mainActivity.stopLoadingSpinner();
            }

        }
    }
}
