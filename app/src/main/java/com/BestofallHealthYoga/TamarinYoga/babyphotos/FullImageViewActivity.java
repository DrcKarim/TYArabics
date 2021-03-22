package com.BestofallHealthYoga.TamarinYoga.babyphotos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

@SuppressLint({"Registered"})
public class FullImageViewActivity extends AppCompatActivity implements View.OnClickListener {
    BabyPhotos babyPhotos;
    CardView facebook;
    String fbPackage = "com.facebook.katana";
    String instaPackage = "com.instagram.android";
    CardView instagram;
    CardView more;
    BaseAppDB myDb;
    PhotoView photoview;
    Toolbar toolbar;
    TextView toolbarText;
    String whaPackage = "com.whatsapp";
    CardView whatsapp;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_full_image_view);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbarText = (TextView) this.toolbar.findViewById(R.id.toolbarText);
        this.whatsapp = (CardView) findViewById(R.id.whatsapp);
//        whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareFile(whaPackage);
//            }
//        });

        this.facebook = (CardView) findViewById(R.id.facebook);
        this.instagram = (CardView) findViewById(R.id.instagram);
        this.more = (CardView) findViewById(R.id.more);
        this.photoview = (PhotoView) findViewById(R.id.photoview);
        initVariable();
        setToolbar();
        setOnClicks();
    }


    public void initVariable() {
        this.myDb = new BaseAppDB(this);
        this.babyPhotos = (BabyPhotos) getIntent().getParcelableExtra(PhotoConstans.BABY_PHOTOS);
        Glide.with((FragmentActivity) this).load(Constant.getPathOfImage(this.babyPhotos.getImageName())).into((ImageView) this.photoview);
    }


    public void setToolbar() {
        setSupportActionBar(this.toolbar);
//        this.toolbar.setNavigationIcon((int) R.mipmap.left_side);
//        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                FullImageViewActivity.this.onSupportNavigateUp();
//            }
//        });
        try {
            this.toolbarText.setText(this.babyPhotos.getTitle().replace("\n", " "));
        } catch (Exception e) {
            this.toolbarText.setText(getResources().getString(R.string.exercisefullimage));
            e.printStackTrace();
        }
    }


    public void setOnClicks() {
        this.whatsapp.setOnClickListener(this);
        this.facebook.setOnClickListener(this);
        this.instagram.setOnClickListener(this);
        this.more.setOnClickListener(this);
    }


    public void initMethods() {
        Fresco.newDraweeControllerBuilder().setUri(Uri.fromFile(new File(Constant.getPathOfImage(this.babyPhotos.getImageName()))));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.facebook) {
            shareFile(this.fbPackage);
        } else if (id == R.id.instagram) {
            shareFile(this.instaPackage);
        } else if (id == R.id.more) {
            shareFile("");
        } else if (id == R.id.whatsapp) {
            shareFile(this.whaPackage);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.full_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.delete) {
            deleteDialog();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void shareFile(String str) {
        try {
            Intent intent = new Intent();
            if (!str.isEmpty()) {
                intent.setPackage(str);
            } else {
                intent = new Intent();
            }
            intent.setAction("android.intent.action.SEND");
            intent.addFlags(1);
            intent.setType("image/*");
            if (Build.VERSION.SDK_INT > 25) {
                intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.BestofallHealthYoga.TamarinYoga.provider", new File(Constant.getPathOfImage(this.babyPhotos.getImageName()))));
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(Constant.getPathOfImage(this.babyPhotos.getImageName()))));
            }
            startActivity(Intent.createChooser(intent, "Share Images...."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteDialog() {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_delete, (ViewGroup) null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ((TextView) inflate.findViewById(R.id.toptext)).setText(getResources().getString(R.string.photoDeleteTitle));
        ((TextView) inflate.findViewById(R.id.message)).setText(getResources().getString(R.string.photoDeleteText));
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawableResource(17170445);
        ((LinearLayout) inflate.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
                FullImageViewActivity fullImageViewActivity = FullImageViewActivity.this;
                fullImageViewActivity.deleteImageFromPrivateFolder(Constant.getPathOfImage(fullImageViewActivity.babyPhotos.getImageName()));
                FullImageViewActivity.this.myDb.updateExerciseImage(FullImageViewActivity.this.babyPhotos.getId(), "");
                Intent intent = new Intent();
                FullImageViewActivity.this.babyPhotos.setImageName("");
                intent.putExtra(PhotoConstans.BABY_PHOTOS, FullImageViewActivity.this.babyPhotos);
                FullImageViewActivity.this.setResult(PhotoConstans.FULL_IMAGE_VIEW, intent);
                FullImageViewActivity.this.finish();
            }
        });
        create.show();
    }

    public boolean deleteImageFromPrivateFolder(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
