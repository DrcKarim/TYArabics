package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.List;

public class FinishExerciseAdapter extends RecyclerView.Adapter<FinishExerciseAdapter.Myview> {
    Context context;
    List<DayWiseActivity> dayWiseActivityArrayList;

    public class Myview extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView totalset;

        public Myview(@NonNull View view) {
            super(view);
            this.exerciseName = (TextView) view.findViewById(R.id.exerciseName);
            this.totalset = (TextView) view.findViewById(R.id.totalset);
        }
    }

    public FinishExerciseAdapter(Context context2, List<DayWiseActivity> arrayList) {
        this.context = context2;
        this.dayWiseActivityArrayList = arrayList;
    }

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_finishlayout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        myview.exerciseName.setText(this.dayWiseActivityArrayList.get(i).getActivityName());
        if (Constant.getacitivityminuison(this.dayWiseActivityArrayList.get(i).getActivityid())) {
            TextView textView = myview.totalset;
            textView.setText(String.valueOf(this.dayWiseActivityArrayList.get(i).getTotalset()) + "s");
            return;
        }
        TextView textView2 = myview.totalset;
        textView2.setText(String.valueOf(this.dayWiseActivityArrayList.get(i).getTotalset()) + "x");
    }

    public int getItemCount() {
        return this.dayWiseActivityArrayList.size();
    }
}
