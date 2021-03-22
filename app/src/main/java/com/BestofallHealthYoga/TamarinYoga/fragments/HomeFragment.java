package com.BestofallHealthYoga.TamarinYoga.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.activities.AllDayDetails;
import com.BestofallHealthYoga.TamarinYoga.activities.InformationOfExercise;
import com.BestofallHealthYoga.TamarinYoga.activities.MyTraining;
import com.BestofallHealthYoga.TamarinYoga.babyphotos.BabyPhotosActivity;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.DemoDB;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView currentday;
    ProgressBar dayprogressbar;
    DemoDB demoDB;
    CardView mytraining;
    String playStoreUrl = "";
    List<Integer> progressWithDayComplete;
    TextView progressper;
    CardView monthlyexcercises;
    CardView startTraining;
    CardView allexcercises;
    String title = "كيف كانت تجربتك معنا؟";
    Toolbar toolbar;
    View view;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        this.playStoreUrl = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
        init();
        setupView();
        setprogress();

        return this.view;
    }

    private void init() {
        if (!AppPref.isFirstLaunch(getActivity())) {
            AppPref.setFirstLaunch(getActivity(), true);
        }
        this.toolbar = (Toolbar) this.view.findViewById(R.id.toolbar);
        this.demoDB = new DemoDB(getActivity());
        this.startTraining = (CardView) this.view.findViewById(R.id.startTraining);
        this.mytraining = (CardView) this.view.findViewById(R.id.mytraining);
        this.allexcercises = (CardView) this.view.findViewById(R.id.allexcercises);
        this.monthlyexcercises = (CardView) this.view.findViewById(R.id.monthly_excercises);
        this.dayprogressbar = (ProgressBar) this.view.findViewById(R.id.dayprogressbar);
        this.progressper = (TextView) this.view.findViewById(R.id.progressper);
        this.currentday = (TextView) this.view.findViewById(R.id.currentday);
        this.dayprogressbar.setMax(100);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    private void setupView() {
        this.startTraining.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), AllDayDetails.class);
                intent.putExtra(HomeFragment.this.getString(R.string.typeId), Constant.MEDIUM);
                intent.setFlags(67108864);
                HomeFragment.this.startActivityForResult(intent, Constant.FROM_MAIN_CODE);
            }
        });
        this.mytraining.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), MyTraining.class);
                intent.setFlags(67108864);
                HomeFragment.this.startActivityForResult(intent, Constant.FROM_MAIN_CODE);
            }
        });

        this.allexcercises.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), InformationOfExercise.class);
                intent.setFlags(67108864);
                HomeFragment.this.startActivity(intent);
            }
        });
        this.monthlyexcercises.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), BabyPhotosActivity.class);
                intent.setFlags(67108864);
                HomeFragment.this.startActivity(intent);
            }
        });


    }

    public void setprogress() {
        this.progressWithDayComplete = new ArrayList<>();
        this.progressWithDayComplete = this.demoDB.getProgressOfTypeWithCompleteDay(Constant.MEDIUM);
        this.dayprogressbar.setProgress(this.progressWithDayComplete.get(1).intValue());
        TextView textView = this.progressper;
        textView.setText(String.valueOf(this.progressWithDayComplete.get(1)) + "٪");
        int intValue = Constant.TOTAL_EXERCISE_DAY - this.progressWithDayComplete.get(0).intValue();
        if (intValue >= Constant.TOTAL_EXERCISE_DAY) {
            this.currentday.setText("");
            return;
        }
        this.currentday.setText("(" + getString(R.string.days) + intValue + ")");
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == Constant.FROM_MAIN_CODE) {
            setprogress();
        }
    }


}
