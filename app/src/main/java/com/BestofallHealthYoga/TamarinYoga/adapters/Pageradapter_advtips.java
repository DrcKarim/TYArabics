package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.BestofallHealthYoga.TamarinYoga.fragments.Tipsadvfragment;

public class Pageradapter_advtips extends FragmentStatePagerAdapter {
    public Pageradapter_advtips(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public int getCount() {
        return 10;
    }

    public Fragment getItem(int i) {
        Tipsadvfragment tipsadvfragment = new Tipsadvfragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos", i);
        tipsadvfragment.setArguments(bundle);
        return tipsadvfragment;
    }
}
