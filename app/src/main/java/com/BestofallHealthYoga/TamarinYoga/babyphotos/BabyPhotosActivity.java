package com.BestofallHealthYoga.TamarinYoga.babyphotos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.activities.MainActivity;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.imagepicker.DefaultCallback;
import com.BestofallHealthYoga.TamarinYoga.imagepicker.EasyImage;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.AdsScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

@SuppressLint({"Registered"})
public class BabyPhotosActivity extends AppCompatActivity implements BabyPhotoListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    BabyPhotosAdapter babyPhotosAdapter;
    List<BabyPhotos> babyPhotosList;
    Context context;
    BottomSheetDialog dialog;
    String id;
    BaseAppDB mydb;
    RecyclerView recyclerview;
    ImageView leftside;
    InterstitialAd mInterstitialAd;


    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        Log.d("","sdsds");
    }

    public void onRationaleAccepted(int i) {
        Log.d("","sdsds");
    }

    public void onRationaleDenied(int i) {
        Log.d("","sdsds");
    }

    public void onStickerClick(String str) {
        Log.d("","sdsds");
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_baby_photos);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initVariable();

        setAdapter();

        MobileAds.initialize(this,"getString(R.string.InterstitialAds");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.InterstitialAds));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }


    public void initVariable() {
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.leftside = (ImageView) findViewById(R.id.back_arrow);
        this.leftside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.mydb = new BaseAppDB(this);
        this.babyPhotosList = new ArrayList<>();
    }


    public void setAdapter() {
        this.babyPhotosList = this.mydb.getPhotos();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerview.setLayoutManager(gridLayoutManager);
        this.babyPhotosAdapter = new BabyPhotosAdapter(getApplicationContext(), this.babyPhotosList, this);
        this.recyclerview.setAdapter(this.babyPhotosAdapter);
    }


    public void onBabyPhotoListener(BabyPhotos babyPhotos) {
        this.id = babyPhotos.getId();
        if (babyPhotos.getImageName().isEmpty()) {
            takeImageDialog();
            return;
        }
        Intent intent = new Intent(this, FullImageViewActivity.class);
        intent.putExtra(PhotoConstans.BABY_PHOTOS, babyPhotos);
        startActivityForResult(intent, PhotoConstans.FULL_IMAGE_VIEW);
    }

    private boolean isHasPermissions(Context context2, String... strArr) {
        return EasyPermissions.hasPermissions(context2, strArr);
    }

    private void requestPermissions(Context context2, String str, int i, String... strArr) {
        EasyPermissions.requestPermissions((Activity) context2, str, i, strArr);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied((Activity) this, list)) {
            new AppSettingsDialog.Builder((Activity) this).build().show();
        }
    }

    private void takeImageDialog() {
        this.dialog = new BottomSheetDialog(this);
        this.dialog.setContentView((int) R.layout.dialog_image_picker);
        this.dialog.setCancelable(true);
        this.dialog.show();
        ((LinearLayout) this.dialog.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BabyPhotosActivity.this.checkPermStorage()) {
                    EasyImage.openGallery((Activity) BabyPhotosActivity.this, 0);
                }
            }
        });
        ((LinearLayout) this.dialog.findViewById(R.id.document)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BabyPhotosActivity.this.checkPermStorage()) {
                    EasyImage.openDocuments((Activity) BabyPhotosActivity.this, 0);
                }
            }
        });
        ((LinearLayout) this.dialog.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BabyPhotosActivity.this.checkPermCamera();
            }
        });
    }


    public void checkPermCamera() {
        if (isHasPermissions(this, "android.permission.CAMERA")) {
            EasyImage.openCameraForImage((Activity) this, 0);
        } else {
            requestPermissions(this, getString(R.string.rationale_camera), Constant.REQUEST_PERM_FILE, "android.permission.CAMERA");
        }
    }


    public boolean checkPermStorage() {
        if (isHasPermissions(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            return true;
        }
        requestPermissions(this, getString(R.string.rationale_save), Constant.REQUEST_PERM_FILE, "android.permission.READ_EXTERNAL_STORAGE");
        return false;
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        EasyImage.handleActivityResult(i, i2, intent, this, new DefaultCallback() {
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {
                if (BabyPhotosActivity.this.dialog != null && BabyPhotosActivity.this.dialog.isShowing()) {
                    BabyPhotosActivity.this.dialog.dismiss();
                }
                Intent intent = new Intent(BabyPhotosActivity.this, BabyPhotoEditor.class);
                intent.putExtra(PhotoConstans.PHOTO_URI, list.get(0).getPath());
                intent.putExtra(PhotoConstans.ID, BabyPhotosActivity.this.id);
                BabyPhotosActivity.this.startActivityForResult(intent, PhotoConstans.PHOTO_EDITOR_CODE);
            }
        });
        if (i == 3001) {
            if (intent != null) {
                BabyPhotos babyPhotos = new BabyPhotos();
                babyPhotos.setId(intent.getStringExtra(PhotoConstans.ID));
                List<BabyPhotos> list = this.babyPhotosList;
                list.get(list.indexOf(babyPhotos)).setImageName(intent.getStringExtra(PhotoConstans.PHOTO_URI));
                this.babyPhotosAdapter.notifyDataSetChanged();
            }
        } else if (i == 3003 && intent != null) {
            BabyPhotos babyPhotos2 = (BabyPhotos) intent.getParcelableExtra(PhotoConstans.BABY_PHOTOS);
            List<BabyPhotos> list2 = this.babyPhotosList;
            list2.set(list2.indexOf(babyPhotos2), babyPhotos2);
            this.babyPhotosAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.backpressad(new AdsScreen() {
            public void backscreen() {
                BabyPhotosActivity.this.finish();
            }
        });
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        mInterstitialAd.show();
    }

}
