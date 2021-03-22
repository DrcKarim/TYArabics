package com.BestofallHealthYoga.TamarinYoga.utils;

import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    public static final float MAX_SCALE = 1.0f;
    public static final float MIN_SCALE = 0.8f;

    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            f = -1.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        float f2 = ((f < 0.0f ? f + 1.0f : 1.0f - f) * 0.19999999f) + 0.8f;
        view.setScaleX(f2);
        view.setScaleY(f2);
        if (Build.VERSION.SDK_INT < 23) {
            view.getParent().requestLayout();
        }
    }
}
