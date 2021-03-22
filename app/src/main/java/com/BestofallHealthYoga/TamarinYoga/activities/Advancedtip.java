package com.BestofallHealthYoga.TamarinYoga.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.appbar.AppBarLayout;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.Pageradapter_advtips;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.DepthPageTransformer;


public class Advancedtip extends Fragment {

    ViewPager f752a;
    ImageView b;
    ImageView c;


    MainActivity h;
    AppBarLayout i;
    int counter = 0;


    InterstitialAd interstitial;
    AdRequest adRequest;

    public void googlefull_ad()
    {
        interstitial = new InterstitialAd(getActivity());
        interstitial.setAdUnitId(AdGlobal.ADFULL);
        adRequest = new AdRequest.Builder().build();
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
        interstitial.loadAd(this.adRequest);
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_advancedtip, viewGroup, false);
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();


        this.f752a = (ViewPager) inflate.findViewById(R.id.viewPager);
        this.b = (ImageView) inflate.findViewById(R.id.previoustip);
        this.c = (ImageView) inflate.findViewById(R.id.nexttip);


        this.f752a.setAdapter(new Pageradapter_advtips(supportFragmentManager));
        this.f752a.setClipToPadding(false);
        this.f752a.setPadding(20, 0, 20, 0);
        this.f752a.setPageMargin(12);
        this.f752a.setPageTransformer(true, new DepthPageTransformer());

        this.f752a.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int i, float f, int i2) {
                ImageView imageView = Advancedtip.this.b;
                if (i == 0) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
                if (i == 9) {
                    Advancedtip.this.c.setVisibility(View.INVISIBLE);
                } else {
                    Advancedtip.this.c.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("", "scrolled");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("", "scrolled_change");
            }


        });
        this.b.setOnClickListener(view -> {

            counter ++;
            if (counter == 5 )
            {
                googlefull_ad();

            }
            else {
                this.c.setVisibility(View.VISIBLE);
                if (this.f752a.getCurrentItem() - 1 == 0) {
                    this.b.setVisibility(View.INVISIBLE);
                }
                ViewPager viewPager = this.f752a;
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
            




        });
        this.c.setOnClickListener(view -> {

            b.setVisibility(View.VISIBLE);
            ViewPager viewPager = f752a;
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            if (f752a.getCurrentItem() + 1 == 10) {
                c.setVisibility(View.INVISIBLE);
            }

        });

        return inflate;
    }


}
