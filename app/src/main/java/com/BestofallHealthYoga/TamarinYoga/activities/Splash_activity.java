package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;

import java.lang.ref.WeakReference;

public class Splash_activity extends AppCompatActivity {
    private static int splashtime = 5000;

    Context context;
    Splash_activity splashactivity;

    WeakReference<Splash_activity> splashactivityweakrefrence;

    class handler implements Runnable {
        handler() {
        }

        public void run() {


            startActivity(new Intent(Splash_activity.this, MainActivity.class));

            finish();

        }
    }




    public Splash_activity() {
        AdGlobal.adcount = 0;
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_splash_activity);
        this.context = this;
        MobileAds.initialize(this.context, (OnInitializationCompleteListener) new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("", "ssd");
            }
        });
        new Handler().postDelayed(new handler(), (long) splashtime);
        this.splashactivity = this;
        this.splashactivityweakrefrence = new WeakReference<>(this.splashactivity);

    }


}
