package com.BestofallHealthYoga.TamarinYoga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.models.DayWiseProgress;
import com.BestofallHealthYoga.TamarinYoga.utils.RecyclerItemClick;

import java.util.List;

public class DayWiseProgressAdapter extends RecyclerView.Adapter<DayWiseProgressAdapter.Myview> {
    Context context;
    List<DayWiseProgress> dayWiseProgressArrayList;
    RecyclerItemClick recyclerItemClick;

    public class Myview extends RecyclerView.ViewHolder {
        TextView dayname;
        CardView dayview;

        public Myview(@NonNull View view) {
            super(view);
            this.dayname = (TextView) view.findViewById(R.id.dayname);
            this.dayview = (CardView) view.findViewById(R.id.dayview);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    DayWiseProgressAdapter.this.recyclerItemClick.onClick(Myview.this.getAdapterPosition());
                }
            });
        }
    }

    public DayWiseProgressAdapter(Context context2, List<DayWiseProgress> arrayList, RecyclerItemClick recyclerItemClick2) {
        this.context = context2;
        this.dayWiseProgressArrayList = arrayList;
        this.recyclerItemClick = recyclerItemClick2;
    }

    @NonNull
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Myview(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_daywise_progress, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull Myview myview, int i) {
        TextView textView = myview.dayname;
        textView.setText(String.valueOf(this.dayWiseProgressArrayList.get(i).getDay()));
        if (this.dayWiseProgressArrayList.get(i).getComplteDay() == 2) {
            myview.dayview.setCardBackgroundColor(this.context.getResources().getColor(R.color.card_primary));
            myview.dayname.setTextColor(this.context.getResources().getColor(R.color.white));
        } else if (this.dayWiseProgressArrayList.get(i).getComplteDay() == 3) {
            myview.dayview.setCardBackgroundColor(this.context.getResources().getColor(R.color.card_primary_dark));
            myview.dayname.setTextColor(this.context.getResources().getColor(R.color.white));
        } else {
            myview.dayview.setCardBackgroundColor(this.context.getResources().getColor(R.color.BackgroundColor));
            myview.dayname.setTextColor(this.context.getResources().getColor(R.color.white));
        }
    }

    public int getItemCount() {
        return this.dayWiseProgressArrayList.size();
    }
}
