package com.BestofallHealthYoga.TamarinYoga.babyphotos;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.utils.BaseApp;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BabyPhotoEditor extends AppCompatActivity implements BabyPhotoListener {
    SimpleDraweeView backphoto;
    String id;
    BaseAppDB myDb;
    String path = "";
    RecyclerView recyclerview;
    StickerAdapter stickerAdapter;
    StickerView stickerView;
    List<String> stickerlist;
    Toolbar toolbar;
    TextView toolbarText;
    ImageView done_menu;

    public void onBabyPhotoListener(BabyPhotos babyPhotos) {
        Log.d("", "ssaf");
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Fresco.initialize(this);
        setContentView((int) R.layout.activity_baby_photo_editor);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        init();
        done_menu = (ImageView) findViewById(R.id.done_menu);
        done_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }


    public void init() {
        this.backphoto = (SimpleDraweeView) findViewById(R.id.backphoto);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarText = (TextView) this.toolbar.findViewById(R.id.toolbarText);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.path = getIntent().getStringExtra(PhotoConstans.PHOTO_URI);
        this.id = getIntent().getStringExtra(PhotoConstans.ID);
        this.stickerlist = new ArrayList<>();
        this.stickerView = (StickerView) findViewById(R.id.stickerView);

        this.myDb = new BaseAppDB(this);
        setToolbar();
        initMethods();
        setAdapter();
        setupStickerMethod();
    }


    public void setToolbar() {
        setSupportActionBar(this.toolbar);


    }


    public void initMethods() {
        Display defaultDisplay = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        setupStickerMethod();
        this.backphoto.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(new File(this.path))).setResizeOptions(new ResizeOptions(displayMetrics.widthPixels, displayMetrics.heightPixels)).build())).setAutoPlayAnimations(true)).build());
    }

    private void setupStickerMethod() {
        BitmapStickerIcon bitmapStickerIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_close_white_18dp), 0);
        bitmapStickerIcon.setIconEvent(new DeleteIconEvent());
        BitmapStickerIcon bitmapStickerIcon2 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_scale_white_18dp), 3);
        bitmapStickerIcon2.setIconEvent(new ZoomIconEvent());
        BitmapStickerIcon bitmapStickerIcon3 = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_flip_white_18dp), 1);
        bitmapStickerIcon3.setIconEvent(new FlipHorizontallyEvent());
        this.stickerView.setIcons(Arrays.asList(bitmapStickerIcon, bitmapStickerIcon2, bitmapStickerIcon3));
        this.stickerView.setBackgroundColor(getResources().getColor(R.color.black));
        this.stickerView.setLocked(false);
        this.stickerView.setConstrained(true);
    }


    public void setAdapter() {
        this.stickerlist = PhotoConstans.getStickerList();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerview.setLayoutManager(gridLayoutManager);
        this.stickerAdapter = new StickerAdapter(getApplicationContext(), this.stickerlist, this);
        this.recyclerview.setAdapter(this.stickerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onStickerClick(String str) {
        try {
            this.stickerView.addSticker(new DrawableSticker(Drawable.createFromStream(getAssets().open(str), (String) null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void saveImage() {
        String str = System.currentTimeMillis() + ".jpg";
        String pathsepreter = "/";
        this.stickerView.save(new File(Constant.getMediaDirctory(BaseApp.getInstance()) + pathsepreter + str));
        Intent intent = new Intent();
        this.myDb.updateExerciseImage(this.id, str);
        intent.putExtra(PhotoConstans.PHOTO_URI, str);
        intent.putExtra(PhotoConstans.ID, this.id);
        setResult(PhotoConstans.PHOTO_EDITOR_CODE, intent);
        finish();
    }
}
