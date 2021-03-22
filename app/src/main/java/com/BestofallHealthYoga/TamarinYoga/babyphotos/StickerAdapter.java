package com.BestofallHealthYoga.TamarinYoga.babyphotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    BabyPhotoListener babyPhotoListener;
    List<String> babyPhotosList;
    Context context;

    public StickerAdapter(Context context2, List<String> list, BabyPhotoListener babyPhotoListener2) {
        this.context = context2;
        this.babyPhotosList = list;
        this.babyPhotoListener = babyPhotoListener2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sticker_layout, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RequestManager with = Glide.with(this.context);
        with.load(Constant.ASSETPATH + this.babyPhotosList.get(i)).into(viewHolder.imageView);
    }

    public int getItemCount() {
        return this.babyPhotosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageview);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    StickerAdapter.this.babyPhotoListener.onStickerClick(StickerAdapter.this.babyPhotosList.get(ViewHolder.this.getAdapterPosition()));
                }
            });
        }
    }
}
