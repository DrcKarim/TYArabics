package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.ActivityInformationAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.ExerciseInformation;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;
import java.util.ArrayList;
import java.util.List;

public class InformationOfExercise extends AppCompatActivity implements RecyclerItemClick {
    RecyclerView activityInformation;
    ActivityInformationAdapter activityInformationAdapter;
    DemoDB demoDB;
    Toolbar toolbar;
    ImageView back_arrow;
    List<ExerciseInformation> userExerciseArrayList;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_information_of_exercise);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        init();
    }

    private void init() {
        this.demoDB = new DemoDB(this);
        this.userExerciseArrayList = new ArrayList<>();
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);


        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InformationOfExercise.this.onSupportNavigateUp();
            }
        });
        this.activityInformation = (RecyclerView) findViewById(R.id.activityInformation);
        setAdapter();
    }

    private void setAdapter() {
        this.userExerciseArrayList = this.demoDB.getExerciseInfoForAll();
        this.activityInformationAdapter = new ActivityInformationAdapter(this, this.userExerciseArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.activityInformation.setLayoutManager(linearLayoutManager);
        this.activityInformation.setAdapter(this.activityInformationAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onClick(int i) {
        Intent intent = new Intent(this, SingleExerciseInfo.class);
        intent.putExtra(getString(R.string.exerciseInfoModel), this.userExerciseArrayList.get(i));
        intent.setFlags(67108864);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        MainActivity.backpressad(new AdsScreen() {
            @Override
            public void backscreen() {
                InformationOfExercise.this.finish();
            }

        });
    }
}
