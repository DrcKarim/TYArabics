package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.UserExercise;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.MyTraining;
import com.BestofallHealthYoga.TamarinYoga.utils.SwipeAndDragHelper;
import java.util.List;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.Myview> implements SwipeAndDragHelper.ActionCompletionContract {
    Context context;

     ItemTouchHelper itemTouchHelper;
    MyTraining recyclerItemClick;
    List<UserExercise> userExercises;

    public AddExerciseAdapter(Context context2, List<UserExercise> arrayList, MyTraining myTraining) {
        this.context = context2;
        this.userExercises = arrayList;
        this.recyclerItemClick = myTraining;
    }

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_addexerciselayout, viewGroup, false));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void onBindViewHolder(@NonNull final Myview myview, int i) {
        myview.activityName.setText(this.userExercises.get(i).getExerciseName());
        if (Constant.getacitivityminuison(this.userExercises.get(i).getActivityId())) {
            TextView textView = myview.totalTime;
            textView.setText(String.valueOf(this.userExercises.get(i).getTotalTime()) + "s");
        } else {
            TextView textView2 = myview.totalTime;
            textView2.setText(String.valueOf(this.userExercises.get(i).getTotalTime()) + "x");
        }
        myview.swapImage.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 0) {
                    return false;
                }
                AddExerciseAdapter.this.itemTouchHelper.startDrag(myview);
                return false;
            }
        });
    }

    public int getItemCount() {
        return this.userExercises.size();
    }

    public void onViewMoved(int i, int i2) {
        UserExercise userExercise = new UserExercise(this.userExercises.get(i));
        this.userExercises.remove(i);
        this.userExercises.add(i2, userExercise);
        notifyItemMoved(i, i2);
    }

    public void reallyMoved(int i, int i2) {
        notifyDataSetChanged();
    }

    public void onViewSwiped(int i) {
        this.userExercises.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, this.userExercises.size());
        if (this.userExercises.isEmpty()) {
            this.recyclerItemClick.onAllDelete(true);
        }
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView activityName;
        ImageView swapImage;
        TextView totalTime;

        public Myview(@NonNull View view) {
            super(view);
            this.activityName = (TextView) view.findViewById(R.id.activityName);
            this.totalTime = (TextView) view.findViewById(R.id.totalTime);
            this.swapImage = (ImageView) view.findViewById(R.id.swapImage);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AddExerciseAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }

    public void setTouchHelper(ItemTouchHelper itemTouchHelper2) {
        this.itemTouchHelper = itemTouchHelper2;
    }
}
