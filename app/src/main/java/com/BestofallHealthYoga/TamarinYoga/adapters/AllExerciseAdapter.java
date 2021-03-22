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
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.List;

public class AllExerciseAdapter extends RecyclerView.Adapter<AllExerciseAdapter.Myview> {
    List<UserExercise> userexercise;
    Context context;
    RecyclerItemClick recyclerItemClick;

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_allactivity_training, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        myview.activityName.setText(this.userexercise.get(i).getExerciseName());
        Glide.with(this.context).load(Constant.getSingleDrawableImage(this.context, this.userexercise.get(i).getActivityId())).into(myview.exersiceImage);
    }

    public int getItemCount() {
        return this.userexercise.size();
    }

    public AllExerciseAdapter(Context context2, List<UserExercise> arrayList, RecyclerItemClick recyclerItemClick2) {
        this.context = context2;
        this.recyclerItemClick = recyclerItemClick2;
        this.userexercise = arrayList;
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView activityName;
        ImageView exersiceImage;

        public Myview(@NonNull View view) {
            super(view);
            this.activityName = (TextView) view.findViewById(R.id.activityName);
            this.exersiceImage = (ImageView) view.findViewById(R.id.exersiceImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AllExerciseAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }
}
