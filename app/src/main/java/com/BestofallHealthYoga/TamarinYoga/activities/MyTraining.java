package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.TrainingAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.MyTrainingModel;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MyTraining extends AppCompatActivity implements RecyclerItemClick {
    Button add;
    DemoDB demoDB;
    List<MyTrainingModel> myTrainingModels;
    RecyclerView mytrening;
    RelativeLayout nolistlayout;
    Toolbar toolbar;
    TrainingAdapter trainingAdapter;
    ImageView back_arrow;
    InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_training);

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
                MyTraining.this.onSupportNavigateUp();
            }
        });
        this.mytrening = (RecyclerView) findViewById(R.id.mytraining);
        this.nolistlayout = (RelativeLayout) findViewById(R.id.nolistlayout);
        this.myTrainingModels = new ArrayList<>();
        this.demoDB = new DemoDB(this);
        this.add = (Button) findViewById(R.id.add);
        this.add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MyTraining.this, AddTraining.class);
                intent.putExtra("isEdit", false);
                MyTraining.this.startActivityForResult(intent, Constant.ADD_TRAINING_CODE);
                MyTraining.this.listEmptyCheck();
            }
        });
    }

    private void setAdapter() {
        this.myTrainingModels = this.demoDB.getAllMyTraining();
        this.trainingAdapter = new TrainingAdapter(this, this.myTrainingModels, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.mytrening.setLayoutManager(linearLayoutManager);
        this.mytrening.setAdapter(this.trainingAdapter);
        listEmptyCheck();
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.ADD_TRAINING_CODE && intent != null) {
            MyTrainingModel myTrainingModel = (MyTrainingModel) intent.getParcelableExtra(getString(R.string.TrainingData));
            if (myTrainingModel != null) {
                this.myTrainingModels.add(myTrainingModel);
                this.trainingAdapter.notifyDataSetChanged();
            }
            listEmptyCheck();
        }
        if (i == Constant.DAY_WISECODE && intent != null) {
            int intExtra = intent.getIntExtra("code", 0);
            int intExtra2 = intent.getIntExtra("position", 1);
            if (intent.getBooleanExtra("isActivityFinish", false)) {
                finish();
            } else if (intExtra == Constant.CODE_DELETE) {
                this.myTrainingModels.remove(intExtra2);
                listEmptyCheck();
            } else if (intExtra == Constant.CODE_UPDATE) {
                this.myTrainingModels.set(intExtra2, (MyTrainingModel) intent.getParcelableExtra(getString(R.string.TrainingData)));
            }
            List<MyTrainingModel> arrayList = this.myTrainingModels;
            if (arrayList != null && !arrayList.isEmpty()) {
                this.trainingAdapter.notifyDataSetChanged();
            }
        }
    }


    public void listEmptyCheck() {
        List<MyTrainingModel> arrayList = this.myTrainingModels;
        if (arrayList == null || arrayList.isEmpty()) {
            this.nolistlayout.setVisibility(View.VISIBLE);
        } else {
            this.nolistlayout.setVisibility(View.GONE);
        }
    }

    public void onClick(int i) {
        int typeid = this.myTrainingModels.get(i).getTypeid();
        Intent intent = new Intent(this, Daywise_Information.class);
        intent.putExtra(getString(R.string.Day), 1);
        intent.putExtra(getString(R.string.typeId), typeid);
        intent.putExtra(getString(R.string.TrainingName), this.myTrainingModels.get(i).getTypename());
        intent.putExtra("position", i);
        intent.setFlags(67108864);
        startActivityForResult(intent, Constant.DAY_WISECODE);
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
