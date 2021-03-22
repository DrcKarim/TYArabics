package com.BestofallHealthYoga.TamarinYoga.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.DayWiseProgressAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseProgress;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllDayDetails extends AppCompatActivity implements RecyclerItemClick {
    Calendar calendar;
    DayWiseProgressAdapter dayWiseProgressAdapter;
    List<DayWiseProgress> dayWiseProgresses;
    TextView dayleft;
    ProgressBar dayprogressbar;
    TextView dayremain;
    RecyclerView daywiseView;
    DemoDB demoDB;
    TextView mainprogressPer;
    List<Integer> progressWithDayComplete;
    ImageView toolImage;
    Toolbar toolbar;
    int typeid;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_all_day_details);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        init();
        setupView();
    }

    private void setupView() {
        setAdapter();
        setupProgress();
    }


    public void setupProgress() {
        this.progressWithDayComplete = new ArrayList<>();
        this.progressWithDayComplete = this.demoDB.getProgressOfTypeWithCompleteDay(this.typeid);
        TextView textView = this.dayleft;
        textView.setText(  this.progressWithDayComplete.get(0) + getString(R.string.left) );
        TextView textView2 = this.mainprogressPer;
        textView2.setText(String.valueOf(this.progressWithDayComplete.get(1)) + "٪");
        this.dayprogressbar.setProgress(this.progressWithDayComplete.get(1).intValue());
        this.calendar = Calendar.getInstance();
        this.calendar.add(5, this.progressWithDayComplete.get(0).intValue());
        this.dayremain.setText(Constant.getFormattedDate(this.calendar.getTimeInMillis(), new SimpleDateFormat("MMMM dd")));
    }

    private void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);



        this.demoDB = new DemoDB(this);
        this.daywiseView = (RecyclerView) findViewById(R.id.daywiseView);
        this.dayleft = (TextView) findViewById(R.id.dayleft);
        this.dayremain = (TextView) findViewById(R.id.dayremain);
        this.mainprogressPer = (TextView) findViewById(R.id.mainprogressPer);
        this.dayprogressbar = (ProgressBar) findViewById(R.id.dayprogressbar);
        this.dayWiseProgresses = new ArrayList<>();
        this.typeid = getIntent().getIntExtra(getString(R.string.typeId), 0);
    }

    private void setAdapter() {
        this.dayWiseProgresses = this.demoDB.getDayList(this.typeid);
        this.dayWiseProgressAdapter = new DayWiseProgressAdapter(this, this.dayWiseProgresses, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.daywiseView.setLayoutManager(gridLayoutManager);
        this.daywiseView.setAdapter(this.dayWiseProgressAdapter);
    }

    public void onClick(int i) {
        int day = this.dayWiseProgresses.get(i).getDay();
        if (this.demoDB.getPreviousDayCompleteOrNot(day - 1, this.typeid) == 0) {
            infoDialog(getString(R.string.dialogPriviousDay));
        } else if (this.dayWiseProgresses.get(i).getComplteDay() == 3) {
            infoDialog("Rest is done");
        } else {
            Intent intent = new Intent(this, Daywise_Information.class);
            intent.putExtra(getString(R.string.Day), day);
            intent.putExtra(getString(R.string.typeId), this.typeid);
            intent.setFlags(67108864);
            startActivityForResult(intent, Constant.DAY_WISECODE);
        }
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.DAY_WISECODE && intent != null && intent.getBooleanExtra("isActivityFinish", false)) {
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.allday_detailsmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.reload) {
            resetExercise();
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    public void resetExercise() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reset_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
        ((LinearLayout) dialog.findViewById(R.id.lin_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                AllDayDetails.this.demoDB.setAllReset(AllDayDetails.this.typeid);
                AllDayDetails.this.dayWiseProgresses.clear();
                AllDayDetails.this.dayWiseProgresses.addAll(AllDayDetails.this.demoDB.getDayList(AllDayDetails.this.typeid));
                AllDayDetails.this.dayWiseProgressAdapter.notifyDataSetChanged();
                AllDayDetails.this.setupProgress();
            }
        });
        ((LinearLayout) dialog.findViewById(R.id.lin_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void infoDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.dialoginfo, (ViewGroup) null);
        builder.setView(inflate);
        ((TextView) inflate.findViewById(R.id.infotext)).setText(str);
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawableResource(17170445);
        create.show();
        ((Button) inflate.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
    }
}
