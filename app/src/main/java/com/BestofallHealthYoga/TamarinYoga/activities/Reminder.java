package com.BestofallHealthYoga.TamarinYoga.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import java.util.Calendar;

public class Reminder extends AppCompatActivity {
    Calendar calendar;
    SwitchCompat dialynotification;
    int mHour;
    int mMinute;
    TextView remindertime;
    LinearLayout timerdialog;
    Toolbar toolbar;
    FrameLayout frameLayout;
    ImageView back_arrow;

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
        setContentView((int) R.layout.activity_reminder);
        init();
        googlefull_ad();
        setNativeLayout();
        if (AppPref.isDAILY(getApplicationContext())) {
            this.dialynotification.setChecked(true);
            this.timerdialog.setVisibility(View.VISIBLE);
            return;
        }
        this.timerdialog.setVisibility(View.GONE);
        this.dialynotification.setChecked(false);
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
    private void init() {
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(AppPref.getReminderTime(getApplicationContext()));
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.back_arrow = (ImageView) findViewById(R.id.back_arrow);
        setSupportActionBar(this.toolbar);

        this.back_arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Reminder.this.onSupportNavigateUp();
            }
        });
        this.frameLayout = (FrameLayout) findViewById(R.id.fl_adplaceholder);

        this.remindertime = (TextView) findViewById(R.id.remindertime);
        this.timerdialog = (LinearLayout) findViewById(R.id.timerdialog);
        this.dialynotification = (SwitchCompat) findViewById(R.id.dailynotification);
        if (this.calendar.get(11) >= 12) {
            TextView textView = this.remindertime;
            StringBuilder sb = new StringBuilder();
            sb.append(this.calendar.get(10) == 0 ? 12 : this.calendar.get(10));
            sb.append(":");
            sb.append(this.calendar.get(12) < 10 ? "0" : "");
            sb.append(this.calendar.get(12));
            sb.append(" PM");
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.remindertime;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.calendar.get(10) == 0 ? 12 : this.calendar.get(10));
            sb2.append(":");
            sb2.append(this.calendar.get(12) < 10 ? "0" : "");
            sb2.append(this.calendar.get(12));
            sb2.append(" AM");
            textView2.setText(sb2.toString());
        }
        this.dialynotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                Log.d("ischecknotification", "" + z);
                if (z) {
                    Reminder.this.timerdialog.setVisibility(View.VISIBLE);
                } else {
                    Reminder.this.timerdialog.setVisibility(View.GONE);
                }
                AppPref.setDAILY(Reminder.this.getApplicationContext(), z);
            }
        });
        this.timerdialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Reminder reminder = Reminder.this;
                reminder.mHour = reminder.calendar.get(11);
                Reminder reminder2 = Reminder.this;
                reminder2.mMinute = reminder2.calendar.get(12);
                new TimePickerDialog(Reminder.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i2) {
                        Reminder.this.calendar.set(11, i);
                        Reminder.this.calendar.set(12, i2);
                        Reminder.this.calendar.set(13, 0);
                        if (i >= 12) {
                            TextView textView = Reminder.this.remindertime;
                            StringBuilder sb = new StringBuilder();
                            sb.append(Reminder.this.calendar.get(10) == 0 ? 12 : Reminder.this.calendar.get(10));
                            sb.append(":");
                            sb.append(Reminder.this.calendar.get(12) < 10 ? "0" : "");
                            sb.append(Reminder.this.calendar.get(12));
                            sb.append(" PM");
                            textView.setText(sb.toString());
                        } else {
                            TextView textView2 = Reminder.this.remindertime;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Reminder.this.calendar.get(10) == 0 ? 12 : Reminder.this.calendar.get(10));
                            sb2.append(":");
                            sb2.append(Reminder.this.calendar.get(12) < 10 ? "0" : "");
                            sb2.append(Reminder.this.calendar.get(12));
                            sb2.append(" AM");
                            textView2.setText(sb2.toString());
                        }
                        AppPref.setReminderTime(Reminder.this.getApplicationContext(), Reminder.this.calendar.getTimeInMillis());
                        AppPref.setDAILY(Reminder.this.getApplicationContext(), true);
                    }
                }, Reminder.this.mHour, Reminder.this.mMinute, false).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
