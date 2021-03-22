package com.BestofallHealthYoga.TamarinYoga.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.ArcProgress;
import com.BestofallHealthYoga.TamarinYoga.utils.BaseApp;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;
import java.util.ArrayList;

public class Day_wise_ExerciseCountDown extends AppCompatActivity {
    FrameLayout adviewcontainer;
    ArrayList<DayWiseActivity> dayWiseActivities;
    DemoDB demoDB;
    TextView exerciseName;
    long exerciseStartTime;
    ImageView exersiceImage;
    TextView headingofSubActivity;
    TextView informationexercise;
    LinearLayout introlayout;
    boolean isExersiceStart = false;
     boolean isPaused = true;
    CountDownTimer myCountdown;
    Button paushplay;
    int progressPos = 0;
    RelativeLayout progresslayout;
    long remainTime = 0;
    Button startExercise;
    FloatingActionButton startRest;
    TextView subExersiceName;
    ArcProgress subProgress;
    ImageView subimage;
    TextView subnextText;
    Toolbar toolbar;
    TextView toolbarText;
    TextView totalset;
    ImageView back_arrow;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_day_wise__exercise_countdown);
        init();
        setupView();
        AdGlobal.loadBanner(this, this.adviewcontainer);
    }

    private void setupView() {
        this.subProgress.setMax(15);
        startIntro();
        this.startExercise.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Day_wise_ExerciseCountDown.this.nextExercise();
            }
        });
        this.startRest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Day_wise_ExerciseCountDown.this.startRest();
            }
        });
        this.paushplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!Day_wise_ExerciseCountDown.this.isPaused) {
                    Day_wise_ExerciseCountDown.this.resumeTimer();
                } else {
                    Day_wise_ExerciseCountDown.this.pauseTimer();
                }
            }
        });
    }

    public void pauseTimer() {
        CountDownTimer countDownTimer = this.myCountdown;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        this.paushplay.setText(getString(R.string.play));
        this.isPaused = false;
    }

    public void resumeTimer() {
        this.myCountdown = new MyCountdown(this.remainTime, (long) Constant.TICKMILLI).start();
        this.paushplay.setText(getString(R.string.pause));
        this.isPaused = true;
    }

    private void startIntro() {
        this.myCountdown = new MyCountdown((long) Constant.REST_OR_INTRO_TOTAL_MILISECONDS, (long) Constant.TICKMILLI).start();
        this.headingofSubActivity.setText(getString(R.string.preparation));
        this.subExersiceName.setText(this.dayWiseActivities.get(this.progressPos).getActivityName());
        Glide.with(getApplicationContext()).load(Constant.getSingleDrawableGIF(this, this.dayWiseActivities.get(this.progressPos).getActivityid())).into(this.subimage);
    }

    private void setupActivityVisiblity() {
        this.progresslayout.setVisibility(View.VISIBLE);
        this.introlayout.setVisibility(View.GONE);
    }

    private void setupIntroOrRestVisibilty() {
        this.progresslayout.setVisibility(View.GONE);
        this.introlayout.setVisibility(View.VISIBLE);
    }

    private void init() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);


        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Day_wise_ExerciseCountDown.this.onSupportNavigateUp();
            }
        });
        this.dayWiseActivities = new ArrayList<>();
        this.demoDB = new DemoDB(this);
        this.dayWiseActivities = getIntent().getParcelableArrayListExtra(getString(R.string.Pendingexercise));
        this.exerciseStartTime = getIntent().getLongExtra(getString(R.string.startExerciseTime), 0);
        this.subProgress = (ArcProgress) findViewById(R.id.subProgress);
        this.exersiceImage = (ImageView) findViewById(R.id.exersiceImage);
        this.introlayout = (LinearLayout) findViewById(R.id.introlayout);
        this.progresslayout = (RelativeLayout) findViewById(R.id.progresslayout);
        this.headingofSubActivity = (TextView) findViewById(R.id.headingofSubActivity);
        this.subnextText = (TextView) findViewById(R.id.subnextText);
        this.subExersiceName = (TextView) findViewById(R.id.subExersiceName);
        this.toolbarText = (TextView) findViewById(R.id.toolbarText);
        this.exerciseName = (TextView) findViewById(R.id.exerciseName);
        this.subimage = (ImageView) findViewById(R.id.subimage);
        this.totalset = (TextView) findViewById(R.id.totalset);
        this.informationexercise = (TextView) findViewById(R.id.information_exercise);
        this.startExercise = (Button) findViewById(R.id.startExercise);
        this.paushplay = (Button) findViewById(R.id.paushplay);
        this.startRest = (FloatingActionButton) findViewById(R.id.startRest);
        this.adviewcontainer = (FrameLayout) findViewById(R.id.ad_view_container);
    }


    public void nextExercise() {
        CountDownTimer countDownTimer = this.myCountdown;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (this.progressPos < this.dayWiseActivities.size()) {
            this.isExersiceStart = true;
            TextView textView = this.toolbarText;
            textView.setText(String.valueOf(this.progressPos + 1) + "/" + this.dayWiseActivities.size());
            setupActivityVisiblity();
            this.exerciseName.setText(this.dayWiseActivities.get(this.progressPos).getActivityName());
            this.informationexercise.setText(this.dayWiseActivities.get(this.progressPos).getActivityInformation());
            if (Constant.getacitivityminuison(this.dayWiseActivities.get(this.progressPos).getActivityid())) {
                TextView textView2 = this.totalset;
                textView2.setText(String.valueOf(this.dayWiseActivities.get(this.progressPos).getTotalset()) + "s");
            } else {
                TextView textView3 = this.totalset;
                textView3.setText(String.valueOf(this.dayWiseActivities.get(this.progressPos).getTotalset()) + getString(R.string.times));
            }
            Glide.with(getApplicationContext()).load(Constant.getSingleDrawableGIF(this, this.dayWiseActivities.get(this.progressPos).getActivityid())).into(this.exersiceImage);
        }
    }


    public void startRest() {
        if (this.progressPos < this.dayWiseActivities.size()) {
            this.isExersiceStart = false;
            this.dayWiseActivities.get(this.progressPos).setIsFinishActivity(Constant.ACTIVITYFINISH);
            this.demoDB.finishActivity(this.dayWiseActivities.get(this.progressPos).getId());
            this.progressPos++;
        }
        if (this.progressPos < this.dayWiseActivities.size()) {
            setupIntroOrRestVisibilty();
            this.myCountdown = new MyCountdown((long) Constant.REST_OR_INTRO_TOTAL_MILISECONDS, (long) Constant.TICKMILLI).start();
            this.subExersiceName.setText(this.dayWiseActivities.get(this.progressPos).getActivityName());
            this.headingofSubActivity.setText(getString(R.string.rest));
            Glide.with(getApplicationContext()).load(Constant.getSingleDrawableGIF(this, this.dayWiseActivities.get(this.progressPos).getActivityid())).into(this.subimage);
            this.toolbarText.setText("");
            this.isPaused = true;
            this.paushplay.setText(getString(R.string.pause));
            return;
        }
        CountDownTimer countDownTimer = this.myCountdown;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Intent intent = new Intent(this, Finish_Exercise.class);
        intent.putExtra(getString(R.string.startExerciseTime), this.exerciseStartTime);
        intent.putParcelableArrayListExtra(getString(R.string.Pendingexercise), this.dayWiseActivities);
        startActivityForResult(intent, Constant.DAYWISEACTIVITYFINISH);
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.DAYWISEACTIVITYFINISH) {
            Intent intent2 = getIntent();
            intent2.putExtra(getString(R.string.isAllExersiceFinish), true);
            setResult(Constant.DAY_WISE_EXERCISE_CODE, intent2);
            finish();
        }
    }

    public class MyCountdown extends CountDownTimer {
        public MyCountdown(long j, long j2) {
            super(j, j2);
        }

        public void onTick(long j) {
            Day_wise_ExerciseCountDown daywiseexercisecoundown = Day_wise_ExerciseCountDown.this;
            daywiseexercisecoundown.remainTime = j;
            long j2 = j / 1000;
            daywiseexercisecoundown.subProgress.setProgress((int) j2);
            if (j2 == 3) {
                BaseApp.speeakSound("three");
            }
            if (j2 == 2) {
                BaseApp.speeakSound("two");
            }
            if (j2 == 1) {
                BaseApp.speeakSound("one");
            }
            if (j2 == 0) {
                BaseApp.speeakSound("lets start");
            }
        }

        public void onFinish() {
            Day_wise_ExerciseCountDown.this.nextExercise();
        }
    }

    @Override
    public void onBackPressed() {
        cancelExercise();
    }


    public void cancelExercise() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cancel_dialog);
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
                if (Day_wise_ExerciseCountDown.this.myCountdown != null) {
                    Day_wise_ExerciseCountDown.this.myCountdown.cancel();
                }
                Intent intent = Day_wise_ExerciseCountDown.this.getIntent();
                intent.putParcelableArrayListExtra(Day_wise_ExerciseCountDown.this.getString(R.string.Pendingexercise), Day_wise_ExerciseCountDown.this.dayWiseActivities);
                intent.putExtra(Day_wise_ExerciseCountDown.this.getString(R.string.isAllExersiceFinish), false);
                Day_wise_ExerciseCountDown.this.setResult(Constant.DAY_WISE_EXERCISE_CODE, intent);
                MainActivity.backpressad(new AdsScreen() {
                    @Override
                    public void backscreen() {
                        Day_wise_ExerciseCountDown.this.finish();
                    }


                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
