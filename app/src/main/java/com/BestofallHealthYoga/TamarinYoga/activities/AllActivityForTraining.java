package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.AllExerciseAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;
import java.util.ArrayList;
import java.util.List;


import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AllActivityForTraining extends AppCompatActivity implements RecyclerItemClick {
    RecyclerView allActivity;
    AllExerciseAdapter allExerciseAdapter;
    DemoDB demoDB;
    Toolbar toolbar;
   List<UserExercise> userExercises;
   ImageView back_arrow;
    InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_all_for_training);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        init();
        setupView();

        MobileAds.initialize(this,"getString(R.string.InterstitialAds");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.InterstitialAds));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void setupView() {
        setAdapter();
    }

    private void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);

        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AllActivityForTraining.this.onSupportNavigateUp();
            }
        });
        this.allActivity = (RecyclerView) findViewById(R.id.allActivity);
        this.userExercises = new ArrayList<>();
        this.demoDB = new DemoDB(this);
    }


    public void setAdapter() {
        this.userExercises = this.demoDB.getAllUserExercises();
        this.allExerciseAdapter = new AllExerciseAdapter(this, this.userExercises, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.allActivity.setLayoutManager(linearLayoutManager);
        this.allActivity.setAdapter(this.allExerciseAdapter);
    }

    public void onClick(int i) {
        Intent intent = new Intent(this, Activity_totalSet.class);
        intent.putExtra(getString(R.string.UserExercises), this.userExercises.get(i));
        startActivityForResult(intent, Constant.TOTAL_SET_FOR_ACTIVITY_CODE);
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.TOTAL_SET_FOR_ACTIVITY_CODE && intent != null) {
            Intent intent2 = new Intent();
            intent2.putExtra(getString(R.string.UserExercises), (UserExercise) intent.getParcelableExtra(getString(R.string.UserExercises)));
            setResult(Constant.ALL_ACTIVITY_CODE, intent2);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        mInterstitialAd.show();
    }
}
