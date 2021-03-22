package com.BestofallHealthYoga.TamarinYoga.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;

import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.ExerciseInformation;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

public class SingleExerciseInfo extends AppCompatActivity {
    int activityId;
    ExerciseInformation exerciseInformation;
    TextView exerciseName;
    ImageView exersiceImage;
    FrameLayout frameLayout;
    TextView informationexercise;
    Toolbar toolbar;
    ImageView back_arrow;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_single_exercise_info);
        init();
        setNativeLayout();
    }

    private void init() {
        this.exerciseInformation = (ExerciseInformation) getIntent().getParcelableExtra(getString(R.string.exerciseInfoModel));
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);

        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SingleExerciseInfo.this.onSupportNavigateUp();
            }
        });
        this.exerciseName = (TextView) findViewById(R.id.exerciseName);
        this.exersiceImage = (ImageView) findViewById(R.id.exersiceImage);
        this.informationexercise = (TextView) findViewById(R.id.information_exercise);
        this.exerciseName.setText(this.exerciseInformation.getExerciseName());
        this.informationexercise.setText(this.exerciseInformation.getExerciseInformation());
        Glide.with(getApplicationContext()).load(Constant.getSingleDrawableGIF(this, this.exerciseInformation.getActivityId())).into(this.exersiceImage);
        this.frameLayout = (FrameLayout) findViewById(R.id.fl_adplaceholder);
    }


    public void setNativeLayout() {
        if (MainActivity.nativeAd != null) {
            try {
                UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_admob_native_large, (ViewGroup) null);
                AdGlobal.populateLarge(MainActivity.nativeAd, unifiedNativeAdView);
                this.frameLayout.removeAllViews();
                this.frameLayout.addView(unifiedNativeAdView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
