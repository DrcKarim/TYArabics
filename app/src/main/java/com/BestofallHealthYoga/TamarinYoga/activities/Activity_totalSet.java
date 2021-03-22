package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

public class Activity_totalSet extends AppCompatActivity {
    TextView activityTitle;
    ImageView addset;
    ImageView exerciseImage;
    ImageView minusset;
    Button okbtn;
    int pos;
    Toolbar toolbar;
    TextView totalset;
    TextView txtNoOftimes;
    UserExercise userExercise;
    ImageView back_arrow;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_total_set);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        init();
        setupView();
    }

    private void setupView() {
        UserExercise userExercise2 = this.userExercise;
        if (userExercise2 != null) {
            this.activityTitle.setText(userExercise2.getExerciseName());
            this.totalset.setText(String.valueOf(this.userExercise.getTotalTime()));
            Glide.with(getApplicationContext()).load(Constant.getSingleDrawableGIF(this, this.userExercise.getActivityId())).into(this.exerciseImage);
            if (Constant.getacitivityminuison(this.userExercise.getActivityId())) {
                this.txtNoOftimes.setText(R.string.no_of_seconds);
            } else {
                this.txtNoOftimes.setText(R.string.no_of_times);
            }
        }
        this.addset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!Activity_totalSet.this.totalset.getText().toString().isEmpty()) {
                    int parseInt = Integer.parseInt(Activity_totalSet.this.totalset.getText().toString()) + 1;
                    if (parseInt >= 10 || parseInt <= 0) {
                        Activity_totalSet.this.totalset.setText(String.valueOf(parseInt));
                        return;
                    }
                    TextView textView = Activity_totalSet.this.totalset;
                    textView.setText(String.valueOf("0" + parseInt));
                }
            }
        });
        this.minusset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int parseInt;
                if (!Activity_totalSet.this.totalset.getText().toString().isEmpty() && (parseInt = Integer.parseInt(Activity_totalSet.this.totalset.getText().toString())) > 1) {
                    int i = parseInt - 1;
                    if (i >= 10 || i <= 0) {
                        Activity_totalSet.this.totalset.setText(String.valueOf(i));
                        return;
                    }
                    Activity_totalSet.this.totalset.setText(String.valueOf("0" + i));
                }
            }
        });
        this.okbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Activity_totalSet.this.userExercise.setTotalTime(Integer.parseInt(Activity_totalSet.this.totalset.getText().toString()));
                Intent intent = new Intent();
                intent.putExtra(Activity_totalSet.this.getString(R.string.UserExercises), Activity_totalSet.this.userExercise);
                intent.putExtra("position", Activity_totalSet.this.pos);
                Activity_totalSet.this.setResult(Constant.TOTAL_SET_FOR_ACTIVITY_CODE, intent);
                Activity_totalSet.this.finish();
            }
        });
    }

    private void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);


        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Activity_totalSet.this.onSupportNavigateUp();
            }
        });
        this.userExercise = (UserExercise) getIntent().getParcelableExtra(getString(R.string.UserExercises));
        this.pos = getIntent().getIntExtra("position", -1);
        this.activityTitle = (TextView) findViewById(R.id.activityTitle);
        this.totalset = (TextView) findViewById(R.id.totalset);
        this.minusset = (ImageView) findViewById(R.id.minusset);
        this.addset = (ImageView) findViewById(R.id.addset);
        this.exerciseImage = (ImageView) findViewById(R.id.exerciseImage);
        this.okbtn = (Button) findViewById(R.id.okbtn);
        this.txtNoOftimes = (TextView) findViewById(R.id.txtNoOftimes);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
