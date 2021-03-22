package com.BestofallHealthYoga.TamarinYoga;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.BestofallHealthYoga.TamarinYoga.activities.MainActivity;



public final  class on_click implements BottomNavigationView.OnNavigationItemSelectedListener {


    private final  MainActivity f7a;

    public on_click(MainActivity superMainActivity) {
        this.f7a = superMainActivity;
    }

    public final boolean onNavigationItemSelected(MenuItem menuItem) {
        return this.f7a.a(menuItem);
    }
}
