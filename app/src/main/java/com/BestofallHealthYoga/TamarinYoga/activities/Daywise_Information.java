package com.BestofallHealthYoga.TamarinYoga.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.adapters.DayWiseActivityAdapter;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.models.ExerciseInformation;
import com.BestofallHealthYoga.TamarinYoga.models.MyTrainingModel;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.ArrayList;
import java.util.List;


public class Daywise_Information extends AppCompatActivity implements RecyclerItemClick {
    String trainingname;
    ArrayList<DayWiseActivity> dayWiseActivities;
    DayWiseActivityAdapter dayWiseActivityAdapter;
    RecyclerView dayWiseActivityView;
    int dayid;
    MenuItem delete;
    DemoDB demoDB;
    MenuItem edit;
    boolean isAllExerciseFinish = true;
    int position;
    Button start;
    Toolbar toolbar;
    TextView toolbarText;
    int typeid;



    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_day_wise__information);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        init();
        setupView();
    }

    private void setupView() {
        setAdapter();
        this.start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!Daywise_Information.this.dayWiseActivities.isEmpty()) {
                    Daywise_Information.this.startExercise();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.DAY_WISE_EXERCISE_CODE && intent != null) {
            this.isAllExerciseFinish = intent.getBooleanExtra(getString(R.string.isAllExersiceFinish), false);
            if (this.isAllExerciseFinish) {
                Intent intent2 = new Intent();
                intent2.putExtra("isActivityFinish", true);
                setResult(Constant.DAY_WISECODE, intent2);
                finish();
            }
        }
        if (i == Constant.FROM_DAY_INFORMATION && intent != null) {
            if (!intent.getBooleanExtra("isAllDelete", false)) {
                Intent intent3 = new Intent();
                intent3.putExtra(getString(R.string.TrainingData), (MyTrainingModel) intent.getParcelableExtra(getString(R.string.TrainingData)));
                intent3.putExtra("code", Constant.CODE_UPDATE);
                intent3.putExtra("position", this.position);
                setResult(Constant.DAY_WISECODE, intent3);
                finish();
            } else {
                this.demoDB.deleteFromExerciseTypeDetails((long) this.typeid);
                Intent intent4 = new Intent();
                intent4.putExtra("code", Constant.CODE_DELETE);
                intent4.putExtra("position", this.position);
                setResult(Constant.DAY_WISECODE, intent4);
                finish();
            }
        }
        if (i == Constant.FROM_REST_ACTIVIRTY_CODE && intent != null) {
            if (intent.getBooleanExtra("isRestFinish", false)) {
                Intent intent5 = new Intent();
                intent5.putExtra("isActivityFinish", true);
                setResult(Constant.DAY_WISECODE, intent5);
                finish();
                return;
            }
            Intent intent6 = new Intent();
            intent6.putExtra("isActivityFinish", false);
            setResult(Constant.DAY_WISECODE, intent6);
            finish();
        }
    }


    public void startExercise() {
        Intent intent = new Intent(this, Day_wise_ExerciseCountDown.class);
        intent.setFlags(67108864);
        intent.putExtra(getString(R.string.Pendingexercise), this.dayWiseActivities);
        intent.putExtra(getString(R.string.startExerciseTime), System.currentTimeMillis());
        startActivityForResult(intent, Constant.DAY_WISE_EXERCISE_CODE);
    }

    private void setAdapter() {
        this.dayWiseActivities = this.demoDB.getDayWiseActivity(this.dayid, this.typeid);
        if (this.dayWiseActivities.get(0).getActivityid() == 0) {
            Intent intent = new Intent(this, RestActivity.class);
            intent.putExtra("id", this.dayWiseActivities.get(0).getId());
            startActivityForResult(intent, Constant.FROM_REST_ACTIVIRTY_CODE);
            return;
        }
        this.dayWiseActivityAdapter = new DayWiseActivityAdapter(this, this.dayWiseActivities, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.dayWiseActivityView.setLayoutManager(linearLayoutManager);
        this.dayWiseActivityView.setAdapter(this.dayWiseActivityAdapter);
    }

    private void init() {
        this.dayid = getIntent().getIntExtra(getString(R.string.Day), 0);
        this.typeid = getIntent().getIntExtra(getString(R.string.typeId), 0);
        this.trainingname = getIntent().getStringExtra(getString(R.string.TrainingName));

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);



        this.demoDB = new DemoDB(this);
        this.dayWiseActivities = new ArrayList<>();
        this.dayWiseActivityView = (RecyclerView) findViewById(R.id.dayWiseActivityView);
        this.start = (Button) findViewById(R.id.start);
        this.toolbarText = (TextView) findViewById(R.id.toolbarText);
        if (this.typeid == 2) {
            TextView textView = this.toolbarText;
            textView.setText(getString(R.string.Day) + this.dayid);
        } else {
            this.toolbarText.setText(this.trainingname);
        }
        this.position = getIntent().getIntExtra("position", -1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.day_wise_exercise_menu, menu);
        this.edit = menu.findItem(R.id.edit);
        this.delete = menu.findItem(R.id.delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.delete) {
            deleteMyTraining();
        } else if (itemId == R.id.edit) {
            editMyTraining();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void editMyTraining() {

        List<UserExercise> arrayList = new ArrayList<>();
        if (this.trainingname != null) {
            for (int i = 0; i < this.dayWiseActivities.size(); i++) {
                UserExercise userExercise = new UserExercise();
                userExercise.setActivityId(this.dayWiseActivities.get(i).getActivityid());
                userExercise.setExerciseName(this.dayWiseActivities.get(i).getActivityName());
                userExercise.setTotalTime(this.dayWiseActivities.get(i).getTotalset());
                arrayList.add(userExercise);
            }
            Intent intent = new Intent(this, AddTraining.class);
            intent.putParcelableArrayListExtra(getString(R.string.UserExercises), (ArrayList<? extends Parcelable>) arrayList);
            intent.putExtra(getString(R.string.TrainingName), this.trainingname);
            intent.putExtra("isEdit", true);
            intent.putExtra("typeId", this.typeid);
            startActivityForResult(intent, Constant.FROM_DAY_INFORMATION);
        }
    }

    public void deleteMyTraining() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_training_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
        ((LinearLayout) dialog.findViewById(R.id.lin_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((LinearLayout) dialog.findViewById(R.id.lin_ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                Daywise_Information.this.demoDB.deleteMyTrainingFromADayWiseActivityDetails((long) Daywise_Information.this.typeid);
                Daywise_Information.this.demoDB.deleteFromExerciseTypeDetails((long) Daywise_Information.this.typeid);
                if (Daywise_Information.this.position != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("code", Constant.CODE_DELETE);
                    intent.putExtra("position", Daywise_Information.this.position);
                    Daywise_Information.this.setResult(Constant.DAY_WISECODE, intent);
                    Daywise_Information.this.finish();
                }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.typeid == 2) {
            this.edit.setVisible(false);
            this.delete.setVisible(false);
        } else {
            this.edit.setVisible(true);
            this.delete.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (this.isAllExerciseFinish) {
            MainActivity.backpressad(new AdsScreen() {
                @Override
                public void backscreen() {
                    Daywise_Information.this.finish();
                }

            });
        } else {
            finish();
        }
    }

    public void onClick(int i) {
        ExerciseInformation exerciseInformation = new ExerciseInformation();
        exerciseInformation.setActivityId(this.dayWiseActivities.get(i).getActivityid());
        exerciseInformation.setExerciseName(this.dayWiseActivities.get(i).getActivityName());
        exerciseInformation.setExerciseInformation(this.dayWiseActivities.get(i).getActivityInformation());
        Intent intent = new Intent(this, SingleExerciseInfo.class);
        intent.putExtra(getString(R.string.exerciseInfoModel), exerciseInformation);
        intent.setFlags(67108864);
        startActivity(intent);
    }
}
