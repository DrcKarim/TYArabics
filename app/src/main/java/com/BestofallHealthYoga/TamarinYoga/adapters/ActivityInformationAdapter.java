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
import com.BestofallHealthYoga.TamarinYoga.models.ExerciseInformation;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.List;

public class ActivityInformationAdapter extends RecyclerView.Adapter<ActivityInformationAdapter.Myview> {
    Context context;
    RecyclerItemClick recyclerItemClick;
    List<ExerciseInformation> userExerciseArrayList;

    public class Myview extends RecyclerView.ViewHolder {
        TextView activityName;
        ImageView exersiceImage;

        public Myview(@NonNull View view) {
            super(view);
            this.activityName = (TextView) view.findViewById(R.id.activityName);
            this.exersiceImage = (ImageView) view.findViewById(R.id.exersiceImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ActivityInformationAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }

    public ActivityInformationAdapter(Context context2, List<ExerciseInformation> arrayList, RecyclerItemClick recyclerItemClick2) {
        this.context = context2;
        this.userExerciseArrayList = arrayList;
        this.recyclerItemClick = recyclerItemClick2;
    }

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_allactivity_training, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        myview.activityName.setText(this.userExerciseArrayList.get(i).getExerciseName());
        Glide.with(this.context).load(Constant.getSingleDrawableImage(this.context, this.userExerciseArrayList.get(i).getActivityId())).into(myview.exersiceImage);
    }

    public int getItemCount() {
        return this.userExerciseArrayList.size();
    }
}
