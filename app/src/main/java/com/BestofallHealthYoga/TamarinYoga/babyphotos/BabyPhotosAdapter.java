package com.BestofallHealthYoga.TamarinYoga.babyphotos;

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
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.util.List;

public class BabyPhotosAdapter extends RecyclerView.Adapter<BabyPhotosAdapter.ViewHolder> {
    BabyPhotoListener babyPhotoListener;
    BabyPhotos babyPhotos;
    List<BabyPhotos> babyPhotosList;
    Context context;

    public BabyPhotosAdapter(Context context2, List<BabyPhotos> list, BabyPhotoListener babyPhotoListener2) {
        this.context = context2;
        this.babyPhotosList = list;
        this.babyPhotoListener = babyPhotoListener2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_baby_photos, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Glide.with(this.context).load(Constant.getPathOfImage(this.babyPhotosList.get(i).imageName)).into(viewHolder.imageview);
        viewHolder.tvtitle.setText(this.babyPhotosList.get(i).getTitle());
    }

    public int getItemCount() {
        return this.babyPhotosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView tvtitle;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.imageview = (ImageView) view.findViewById(R.id.imageview);
            this.tvtitle = (TextView) view.findViewById(R.id.tv_title);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    BabyPhotosAdapter.this.babyPhotoListener.onBabyPhotoListener(BabyPhotosAdapter.this.babyPhotosList.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}
