package com.BestofallHealthYoga.TamarinYoga.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.FinishExerciseAdapter;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Finish_Exercise extends AppCompatActivity {
    ArrayList<DayWiseActivity> dayWiseActivities;
    int dayid;
    long exerciseStartTime;
    FinishExerciseAdapter finishExerciseAdapter;
    RecyclerView finishview;
    NestedScrollView screenshortlayout;
    Toolbar toolbar;
    TextView totalExercises;
    TextView totaltime;

    InterstitialAd interstitial;
    AdRequest adRequest;

    public void googlefull_ad()
    {
        interstitial = new InterstitialAd(this);
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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_finish__exercise);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        googlefull_ad();

        init();
        setupView();
    }

    private void setupView() {
        setAdapter();
        setupTotalTime();
    }

    @SuppressLint({"SetTextI18n"})
    private void setupTotalTime() {
        int ceil = (int) ((System.currentTimeMillis() - this.exerciseStartTime) / 60000);
        if (ceil > 0) {
            TextView textView = this.totaltime;
            textView.setText(ceil + getString(R.string.minutes));
            return;
        }
        this.totaltime.setText("1 الدقائق");
    }

    private void setAdapter() {
        this.finishExerciseAdapter = new FinishExerciseAdapter(this, this.dayWiseActivities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.finishview.setLayoutManager(linearLayoutManager);
        this.finishview.setAdapter(this.finishExerciseAdapter);
    }


    public void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.xletter);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Finish_Exercise.this.onBackPressed();
            }
        });
        this.totalExercises = (TextView) findViewById(R.id.totalExercises);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.exerciseStartTime = getIntent().getLongExtra(getString(R.string.startExerciseTime), 0);
        this.dayWiseActivities = new ArrayList<>();
        this.dayWiseActivities = getIntent().getParcelableArrayListExtra(getString(R.string.Pendingexercise));
        this.dayid = getIntent().getIntExtra(getString(R.string.Day), 0);
        this.finishview = (RecyclerView) findViewById(R.id.finishview);
        this.totaltime = (TextView) findViewById(R.id.totaltime);
        this.screenshortlayout = (NestedScrollView) findViewById(R.id.screenshortlayout);
        this.totalExercises.setText(String.valueOf(this.dayWiseActivities.size()));
    }

    @Override
    public void onBackPressed() {
        setResult(Constant.DAYWISEACTIVITYFINISH);
        MainActivity.backpressad(new AdsScreen() {
            @Override
            public void backscreen() {
                Finish_Exercise.this.finish();
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finishactivitymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.share) {
            captureScreenshot(this.screenshortlayout);
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private Bitmap getBitmapFromView(View view, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(getResources().getColor(R.color.bg_color));
        }
        view.draw(canvas);
        return createBitmap;
    }

    public void captureScreenshot(NestedScrollView nestedScrollView) {
        try {
            Toast.makeText(getApplicationContext(), getString(R.string.share_your_result), Toast.LENGTH_LONG).show();
            Bitmap bitmapFromView = getBitmapFromView(nestedScrollView, nestedScrollView.getChildAt(0).getHeight(), nestedScrollView.getChildAt(0).getWidth());
            File file = new File(Constant.getMediaDir(getApplicationContext()), "FlatWorkoutImg.jpg");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmapFromView.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            Uri uriForFile = FileProvider.getUriForFile(getApplicationContext(), "com.BestofallHealthYoga.TamarinYoga.provider", file);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.SUBJECT", "");
            StringBuilder sb = new StringBuilder();
            sb.append( getString(R.string.thnks_to_yoga_wkt) + "\nhttps://play.google.com/store/apps/details?id=");
            sb.append(getPackageName());
            intent.putExtra("android.intent.extra.TEXT", sb.toString());
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            startActivity(Intent.createChooser(intent, getString(R.string.share_screenshot)));
            Toast.makeText(getApplicationContext(), getString(R.string.no_app_avilable), Toast.LENGTH_SHORT).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
