package com.BestofallHealthYoga.TamarinYoga.imagepicker;

import android.content.Context;
import android.preference.PreferenceManager;

public class EasyImageConfiguration  {
    private Context context;

    EasyImageConfiguration(Context context2) {
        this.context = context2;
    }

    public EasyImageConfiguration setImagesFolderName(String str) {
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString("pl.aprilapps.folder_name", str).commit();
        return this;
    }

    public EasyImageConfiguration setAllowMultiplePickInGallery(boolean z) {
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean("pl.aprilapps.easyimage.allow_multiple", z).commit();
        return this;
    }

    public EasyImageConfiguration setCopyTakenPhotosToPublicGalleryAppFolder(boolean z) {
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean("pl.aprilapps.easyimage.copy_taken_photos", z).commit();
        return this;
    }

    public EasyImageConfiguration setCopyPickedImagesToPublicGalleryAppFolder(boolean z) {
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean("pl.aprilapps.easyimage.copy_picked_images", z).commit();
        return this;
    }

    public String getFolderName() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getString("pl.aprilapps.folder_name", "EasyImage");
    }

    public boolean allowsMultiplePickingInGallery() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("pl.aprilapps.easyimage.allow_multiple", true);
    }

    public boolean shouldCopyTakenPhotosToPublicGalleryAppFolder() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("pl.aprilapps.easyimage.copy_taken_photos", false);
    }

    public boolean shouldCopyPickedImagesToPublicGalleryAppFolder() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean("pl.aprilapps.easyimage.copy_picked_images", false);
    }
}
