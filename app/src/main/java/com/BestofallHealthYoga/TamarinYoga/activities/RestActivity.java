package com.BestofallHealthYoga.TamarinYoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;

public class RestActivity extends AppCompatActivity {
    DemoDB demoDB;
    Button finishRest;
    int id;
    Toolbar toolbar;
    ImageView back_arrow;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_rest);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        init();
    }

    private void init() {
        this.demoDB = new DemoDB(this);
        this.id = getIntent().getIntExtra("id", 0);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        this.finishRest = (Button) findViewById(R.id.finishRest);
        setSupportActionBar(this.toolbar);

        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RestActivity.this.onSupportNavigateUp();
            }
        });
        this.finishRest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RestActivity.this.id != 0) {
                    RestActivity.this.demoDB.finishActivity(RestActivity.this.id);
                    Intent intent = new Intent();
                    intent.putExtra("isRestFinish", true);
                    RestActivity.this.setResult(Constant.FROM_REST_ACTIVIRTY_CODE, intent);
                    MainActivity.backpressad(new AdsScreen() {
                        public void backscreen() {
                            RestActivity.this.finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isRestFinish", false);
        setResult(Constant.FROM_REST_ACTIVIRTY_CODE, intent);
        finish();
    }
}
