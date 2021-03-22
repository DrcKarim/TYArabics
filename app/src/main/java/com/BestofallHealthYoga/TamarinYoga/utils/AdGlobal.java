package com.BestofallHealthYoga.TamarinYoga.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.BestofallHealthYoga.TamarinYoga.R;

import java.util.List;

public class AdGlobal {

    private static String strPUBLISHERID = "ca-app-pub-1332480513983290~9318610374";

    private static String strADBANNER = "ca-app-pub-1332480513983290/5026838113";
    private static String strADFULL = "ca-app-pub-1332480513983290/9896021413";
    private static String strADNATIVE = "ca-app-pub-1332480513983290/7454864361";


    public static final String ADBANNER = strADBANNER;
    public static final String ADFULL = strADFULL;
    public static final String NATIVE_ID = strADNATIVE;
    public static  int adcount = 0;
    public static final int ADLIMIT = 3;
    public static final boolean NPAFLAG = false;

    protected static String[] publisherIds = {
            strPUBLISHERID
    };


    public static AdView loadBanner(Context context, FrameLayout frameLayout) {
        AdRequest adRequest;
        AdView adView = new AdView(context);
        adView.setAdUnitId(ADBANNER);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);
        adView.setAdSize(getAdSize(context));
        if (NPAFLAG) {
            Log.d("NPA", "" + NPAFLAG);
            Bundle bundle = new Bundle();
            bundle.putString("npa", "1");
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, bundle).build();
        } else {
            Log.d("NPA", "" + NPAFLAG);
            adRequest = new AdRequest.Builder().build();
        }
        adView.loadAd(adRequest);
        return adView;
    }

    public static AdSize getAdSize(Context context) {
        Display defaultDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }

    public static void populateLarge(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.appinstall_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.appinstall_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.appinstall_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.appinstall_app_icon));
        unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.appinstall_price));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.appinstall_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.appinstall_store));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        if (unifiedNativeAd.getIcon() != null) {
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
        } else {
            unifiedNativeAdView.getIconView().setVisibility(View.INVISIBLE);
        }
        ImageView imageView = (ImageView) unifiedNativeAdView.findViewById(R.id.appinstall_image);
        unifiedNativeAdView.setImageView(imageView);
        imageView.setVisibility(View.VISIBLE);
        List<NativeAd.Image> images = unifiedNativeAd.getImages();
        if (images != null && !images.isEmpty()) {
            imageView.setImageDrawable(images.get(0).getDrawable());
        }
        if (unifiedNativeAd.getPrice() != null) {
            unifiedNativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getPriceView()).setText(unifiedNativeAd.getPrice());
        } else {
            unifiedNativeAdView.getPriceView().setVisibility(View.INVISIBLE);
        }
        if (unifiedNativeAd.getStore() != null) {
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
        } else {
            unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
        }
        if (unifiedNativeAd.getStarRating() != null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
        } else {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }


}
