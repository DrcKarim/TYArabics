package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.MyTrainingModel;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.Myview> {
    Context context;
    RecyclerItemClick recyclerItemClick;
    List<MyTrainingModel> trainingModels;

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_mytraining, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        myview.title.setText(this.trainingModels.get(i).getTypename());
        TextView textView = myview.countexercise;
        textView.setText(String.valueOf(this.trainingModels.get(i).getTotalExercise()) + context.getString(R.string.exercises));
    }

    public int getItemCount() {
        return this.trainingModels.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView countexercise;
        TextView title;

        public Myview(@NonNull View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.countexercise = (TextView) view.findViewById(R.id.countexercise);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TrainingAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }

    public TrainingAdapter(Context context2, List<MyTrainingModel> arrayList, RecyclerItemClick recyclerItemClick2) {
        this.context = context2;
        this.trainingModels = arrayList;
        this.recyclerItemClick = recyclerItemClick2;
    }
}
