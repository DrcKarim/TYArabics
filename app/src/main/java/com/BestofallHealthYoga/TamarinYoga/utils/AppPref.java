package com.BestofallHealthYoga.TamarinYoga.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppPref {
    static final String HEIGHTINCH_CALCULATION = "HEIGHT_INCH_calculation";
    static final String HEIGHT_CALCULATION = "HEIGHT_calculation";
    static final String IS_DAILY = "IS_DAILY";
    static final String IS_FIRST_LUNCH = "IS_FIRST_LUNCH";
    static final String ISKG_CALCULATION = "IS_KG_calculation";
    static final String IS_METRIC_WAIST = "isMetricWaist";
    static final String IS_METRIC_WEIGHT = "isMetricWeight";
    static final String IS_RATEUS = "IS_RATEUS";
    static final String IS_SAVE = "IS_SAVE";
    static final String MY_PREFRENCE = "userPref";
    static final String REMINDER_TIME = "reminderTime";
    static final String WEIGHT_CALCULATION = "WEIGHT_calculation";

    public static boolean rateus(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_RATEUS, false);
    }

    public static void setRateUs(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_RATEUS, z);
        edit.commit();
    }




    public static boolean isFirstLaunch(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_FIRST_LUNCH, false);
    }

    public static void setFirstLaunch(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_FIRST_LUNCH, z);
        edit.commit();
        Constant.remind24(context);
        Constant.remind3hour(context);
    }


    public static boolean isDAILY(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_DAILY, true);
    }

    public static void setDAILY(Context context, boolean z) {
        Log.d("isDaily", "setDaily" + z);
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_DAILY, z);
        edit.commit();
        if (z) {
            Constant.remind24(context);
            Constant.remind3hour(context);
        }
    }

    public static void setReminderTime(Context context, long j) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putLong(REMINDER_TIME, j);
        edit.commit();
    }

    public static long getReminderTime(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getLong(REMINDER_TIME, 1548207000000L);
    }


    public static float getWeightcalculation(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getFloat(WEIGHT_CALCULATION, 70.0f);
    }

    public static void setWeightcalculation(Context context, float f) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putFloat(WEIGHT_CALCULATION, f);
        edit.commit();
    }

    public static int getHeightinchcalculation(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getInt(HEIGHTINCH_CALCULATION, 5);
    }

    public static void setHeightinchcalculation(Context context, int i) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putInt(HEIGHTINCH_CALCULATION, i);
        edit.commit();
    }

    public static int getHeightcalculation(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getInt(HEIGHT_CALCULATION, 165);
    }

    public static void setHeightcalculation(Context context, int i) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putInt(HEIGHT_CALCULATION, i);
        edit.commit();
    }


    public static boolean iskglbcalcu(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(ISKG_CALCULATION, true);
    }

    public static void setKglbcalculation(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(ISKG_CALCULATION, z);
        edit.commit();
    }

    public static boolean isSave(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_SAVE, false);
    }

    @SuppressLint({"ApplySharedPref"})
    public static void setSave(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_SAVE, z);
        edit.commit();
    }

    public static boolean isMatrixWeight(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_METRIC_WEIGHT, true);
    }

    public static void setMatrixWeight(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_METRIC_WEIGHT, z);
        edit.commit();
    }

    public static boolean isMatrixWaist(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_METRIC_WAIST, true);
    }

    public static void setMatrixWaist(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_METRIC_WAIST, z);
        edit.commit();
    }
}
