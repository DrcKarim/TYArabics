package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseActivity;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.List;

public class DayWiseActivityAdapter extends RecyclerView.Adapter<DayWiseActivityAdapter.Myview> {
    Context context;
    List<DayWiseActivity> dayWiseActivityArrayList;
    RecyclerItemClick recyclerItemClick;

    public class Myview extends RecyclerView.ViewHolder {
        TextView exerciseName;
        ImageView exersiceImage;
        TextView totalSet;

        public Myview(@NonNull View view) {
            super(view);
            this.exerciseName = (TextView) view.findViewById(R.id.exerciseName);
            this.totalSet = (TextView) view.findViewById(R.id.totalSet);
            this.exersiceImage = (ImageView) view.findViewById(R.id.exersiceImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    DayWiseActivityAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }

    public DayWiseActivityAdapter(Context context2, List<DayWiseActivity> arrayList, RecyclerItemClick recyclerItemClick2) {
        this.context = context2;
        this.dayWiseActivityArrayList = arrayList;
        this.recyclerItemClick = recyclerItemClick2;
    }

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_daywise_activity, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        Glide.with(this.context).load(Constant.getSingleDrawableGIF(this.context, this.dayWiseActivityArrayList.get(i).getActivityid())).into(myview.exersiceImage);
        myview.exerciseName.setText(this.dayWiseActivityArrayList.get(i).getActivityName());
        if (Constant.getacitivityminuison(this.dayWiseActivityArrayList.get(i).getActivityid())) {
            TextView textView = myview.totalSet;
            textView.setText(String.valueOf(this.dayWiseActivityArrayList.get(i).getTotalset()) + "s");
            return;
        }
        TextView textView2 = myview.totalSet;
        textView2.setText(String.valueOf(this.dayWiseActivityArrayList.get(i).getTotalset()) + "x");
    }

    public int getItemCount() {
        return this.dayWiseActivityArrayList.size();
    }
}
