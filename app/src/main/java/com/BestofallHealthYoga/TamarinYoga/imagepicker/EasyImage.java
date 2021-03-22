package com.BestofallHealthYoga.TamarinYoga.imagepicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EasyImage {
    private static final String KEY_LAST_CAMERA_PHOTO = "pl.aprilapps.easyphotopicker.last_photo";
    private static final String KEY_LAST_CAMERA_VIDEO = "pl.aprilapps.easyphotopicker.last_video";
    private static final String KEY_PHOTO_URI = "pl.aprilapps.easyphotopicker.photo_uri";
    private static final String KEY_TYPE = "pl.aprilapps.easyphotopicker.type";
    private static final String KEY_VIDEO_URI = "pl.aprilapps.easyphotopicker.video_uri";


    public interface Callbacks {
        void onCanceled(ImageSource imageSource, int i);

        void onImagePickerError(Exception exc, ImageSource imageSource, int i);

        void onImagesPicked(@NonNull List<File> list, ImageSource imageSource, int i);
    }

    public enum ImageSource {
        GALLERY,
        DOCUMENTS,
        CAMERA_IMAGE,
        CAMERA_VIDEO
    }

    public static boolean willHandleActivityResult(int i, int i2, Intent intent) {
        return i == 32768 || i == 4972 || i == 9068 || i == 2924;
    }

    private static Uri createCameraPictureFile(@NonNull Context context) throws IOException {
        File cameraPicturesLocation = EasyImageFiles.getCameraPicturesLocation(context);
        Uri uriToFile = EasyImageFiles.getUriToFile(context, cameraPicturesLocation);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(KEY_PHOTO_URI, uriToFile.toString());
        edit.putString(KEY_LAST_CAMERA_PHOTO, cameraPicturesLocation.toString());
        edit.apply();
        return uriToFile;
    }

    private static Uri createCameraVideoFile(@NonNull Context context) throws IOException {
        File cameraVideoLocation = EasyImageFiles.getCameraVideoLocation(context);
        Uri uriToFile = EasyImageFiles.getUriToFile(context, cameraVideoLocation);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(KEY_VIDEO_URI, uriToFile.toString());
        edit.putString(KEY_LAST_CAMERA_VIDEO, cameraVideoLocation.toString());
        edit.apply();
        return uriToFile;
    }

    private static Intent createDocumentsIntent(@NonNull Context context, int i) {
        storeType(context, i);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        return intent;
    }

    private static Intent createGalleryIntent(@NonNull Context context, int i) {
        storeType(context, i);
        Intent plainGalleryPickerIntent = plainGalleryPickerIntent();
        if (Build.VERSION.SDK_INT >= 18) {
            plainGalleryPickerIntent.putExtra("android.intent.extra.ALLOW_MULTIPLE", configuration(context).allowsMultiplePickingInGallery());
        }
        return plainGalleryPickerIntent;
    }

    private static Intent createCameraForImageIntent(@NonNull Context context, int i) {
        storeType(context, i);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            Uri createCameraPictureFile = createCameraPictureFile(context);
            grantWritePermission(context, intent, createCameraPictureFile);
            intent.putExtra("output", createCameraPictureFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    private static Intent createCameraForVideoIntent(@NonNull Context context, int i) {
        storeType(context, i);
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        try {
            Uri createCameraVideoFile = createCameraVideoFile(context);
            grantWritePermission(context, intent, createCameraVideoFile);
            intent.putExtra("output", createCameraVideoFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    private static void revokeWritePermission(@NonNull Context context, Uri uri) {
        context.revokeUriPermission(uri, 3);
    }

    private static void grantWritePermission(@NonNull Context context, Intent intent, Uri uri) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
            context.grantUriPermission(resolveInfo.activityInfo.packageName, uri, 3);
        }
    }

    private static Intent createChooserIntent(@NonNull Context context, @Nullable String str, int i) throws IOException {
        return createChooserIntent(context, str, false, i);
    }

    private static Intent createChooserIntent(@NonNull Context context, @Nullable String str, boolean z, int i) throws IOException {
        Intent intent;
        storeType(context, i);
        Uri createCameraPictureFile = createCameraPictureFile(context);
        List<Intent> arrayList = new ArrayList<>();
        Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
        for (ResolveInfo next : context.getPackageManager().queryIntentActivities(intent2, 0)) {
            String str2 = next.activityInfo.packageName;
            Intent intent3 = new Intent(intent2);
            intent3.setComponent(new ComponentName(next.activityInfo.packageName, next.activityInfo.name));
            intent3.setPackage(str2);
            intent3.putExtra("output", createCameraPictureFile);
            grantWritePermission(context, intent3, createCameraPictureFile);
            arrayList.add(intent3);
        }
        if (z) {
            intent = createGalleryIntent(context, i);
        } else {
            intent = createDocumentsIntent(context, i);
        }
        Intent createChooser = Intent.createChooser(intent, str);
        createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", arrayList.toArray(new Parcelable[arrayList.size()]));
        return createChooser;
    }

    private static void storeType(@NonNull Context context, int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(KEY_TYPE, i).commit();
    }

    private static int restoreType(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_TYPE, 0);
    }

    public static void openChooserWithDocuments(Activity activity, @Nullable String str, int i) {
        try {
            activity.startActivityForResult(createChooserIntent(activity, str, i), 35692);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChooserWithDocuments(Fragment fragment, @Nullable String str, int i) {
        try {
            fragment.startActivityForResult(createChooserIntent(fragment.getActivity(), str, i), 35692);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChooserWithDocuments(android.app.Fragment fragment, @Nullable String str, int i) {
        try {
            fragment.startActivityForResult(createChooserIntent(fragment.getActivity(), str, i), 35692);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChooserWithGallery(Activity activity, @Nullable String str, int i) {
        try {
            activity.startActivityForResult(createChooserIntent(activity, str, true, i), 37740);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChooserWithGallery(Fragment fragment, @Nullable String str, int i) {
        try {
            fragment.startActivityForResult(createChooserIntent(fragment.getActivity(), str, true, i), 37740);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChooserWithGallery(android.app.Fragment fragment, @Nullable String str, int i) {
        try {
            fragment.startActivityForResult(createChooserIntent(fragment.getActivity(), str, true, i), 37740);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openDocuments(Activity activity, int i) {
        activity.startActivityForResult(createDocumentsIntent(activity, i), 2924);
    }

    public static void openDocuments(Fragment fragment, int i) {
        fragment.startActivityForResult(createDocumentsIntent(fragment.getContext(), i), 2924);
    }

    public static void openDocuments(android.app.Fragment fragment, int i) {
        fragment.startActivityForResult(createDocumentsIntent(fragment.getActivity(), i), 2924);
    }

    public static void openGallery(Activity activity, int i) {
        activity.startActivityForResult(createGalleryIntent(activity, i), 4972);
    }

    public static void openGallery(Fragment fragment, int i) {
        fragment.startActivityForResult(createGalleryIntent(fragment.getContext(), i), 4972);
    }

    public static void openGallery(android.app.Fragment fragment, int i) {
        fragment.startActivityForResult(createGalleryIntent(fragment.getActivity(), i), 4972);
    }

    public static void openCameraForImage(Activity activity, int i) {
        activity.startActivityForResult(createCameraForImageIntent(activity, i), 9068);
    }

    public static void openCameraForImage(Fragment fragment, int i) {
        fragment.startActivityForResult(createCameraForImageIntent(fragment.getActivity(), i), 9068);
    }

    public static void openCameraForImage(android.app.Fragment fragment, int i) {
        fragment.startActivityForResult(createCameraForImageIntent(fragment.getActivity(), i), 9068);
    }

    public static void openCameraForVideo(Activity activity, int i) {
        activity.startActivityForResult(createCameraForVideoIntent(activity, i), 17260);
    }

    public static void openCameraForVideo(Fragment fragment, int i) {
        fragment.startActivityForResult(createCameraForVideoIntent(fragment.getActivity(), i), 17260);
    }

    public static void openCameraForVideo(android.app.Fragment fragment, int i) {
        fragment.startActivityForResult(createCameraForVideoIntent(fragment.getActivity(), i), 17260);
    }

    @Nullable
    private static File takenCameraPicture(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LAST_CAMERA_PHOTO, (String) null);
        if (string != null) {
            return new File(string);
        }
        return null;
    }

    @Nullable
    private static File takenCameraVideo(Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LAST_CAMERA_VIDEO, (String) null);
        if (string != null) {
            return new File(string);
        }
        return null;
    }

    public static void handleActivityResult(int i, int i2, Intent intent, Activity activity, @NonNull Callbacks callbacks) {
        if ((i & 876) > 0) {
            int i3 = i & -32769;
            if (i3 != 4972 && i3 != 9068 && i3 != 17260 && i3 != 2924) {
                return;
            }
            if (i2 == -1) {
                if (i3 == 2924 && !isPhoto(intent)) {
                    onPictureReturnedFromDocuments(intent, activity, callbacks);
                } else if (i3 == 4972 && !isPhoto(intent)) {
                    onPictureReturnedFromGallery(intent, activity, callbacks);
                } else if (i3 == 9068) {
                    onPictureReturnedFromCamera(activity, callbacks);
                } else if (i3 == 17260) {
                    onVideoReturnedFromCamera(activity, callbacks);
                } else if (isPhoto(intent)) {
                    onPictureReturnedFromCamera(activity, callbacks);
                } else {
                    onPictureReturnedFromDocuments(intent, activity, callbacks);
                }
            } else if (i3 == 2924) {
                callbacks.onCanceled(ImageSource.DOCUMENTS, restoreType(activity));
            } else if (i3 == 4972) {
                callbacks.onCanceled(ImageSource.GALLERY, restoreType(activity));
            } else {
                callbacks.onCanceled(ImageSource.CAMERA_IMAGE, restoreType(activity));
            }
        }
    }

    private static boolean isPhoto(Intent intent) {
        return intent == null || (intent.getData() == null && intent.getClipData() == null);
    }

    private static Intent plainGalleryPickerIntent() {
        return new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    public static boolean canDeviceHandleGallery(@NonNull Context context) {
        return plainGalleryPickerIntent().resolveActivity(context.getPackageManager()) != null;
    }

    public static File lastlyTakenButCanceledPhoto(@NonNull Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LAST_CAMERA_PHOTO, (String) null);
        if (string == null) {
            return null;
        }
        File file = new File(string);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static File lastlyTakenButCanceledVideo(@NonNull Context context) {
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LAST_CAMERA_VIDEO, (String) null);
        if (string == null) {
            return null;
        }
        File file = new File(string);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    private static void onPictureReturnedFromDocuments(Intent intent, Activity activity, @NonNull Callbacks callbacks) {
        try {
            File pickedExistingPicture = EasyImageFiles.pickedExistingPicture(activity, intent.getData());
            callbacks.onImagesPicked(EasyImageFiles.singleFileList(pickedExistingPicture), ImageSource.DOCUMENTS, restoreType(activity));
            if (configuration(activity).shouldCopyPickedImagesToPublicGalleryAppFolder()) {
                EasyImageFiles.copyFilesInSeparateThread(activity, EasyImageFiles.singleFileList(pickedExistingPicture));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e, ImageSource.DOCUMENTS, restoreType(activity));
        }
    }

    private static void onPictureReturnedFromGallery(Intent intent, Activity activity, @NonNull Callbacks callbacks) {
        try {
            ClipData clipData = intent.getClipData();

            List<File> arrayList = new ArrayList<>();
            if (clipData == null) {
                arrayList.add(EasyImageFiles.pickedExistingPicture(activity, intent.getData()));
            } else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    arrayList.add(EasyImageFiles.pickedExistingPicture(activity, clipData.getItemAt(i).getUri()));
                }
            }
            if (configuration(activity).shouldCopyPickedImagesToPublicGalleryAppFolder()) {
                EasyImageFiles.copyFilesInSeparateThread(activity, arrayList);
            }
            callbacks.onImagesPicked(arrayList, ImageSource.GALLERY, restoreType(activity));
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e, ImageSource.GALLERY, restoreType(activity));
        }
    }

    private static void onPictureReturnedFromCamera(Activity activity, @NonNull Callbacks callbacks) {
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(activity).getString(KEY_PHOTO_URI, (String) null);
            if (!TextUtils.isEmpty(string)) {
                revokeWritePermission(activity, Uri.parse(string));
            }
            File takenCameraPicture = takenCameraPicture(activity);

            List<File> arrayList = new ArrayList<>();

            arrayList.add(takenCameraPicture);
            if (takenCameraPicture == null) {
                callbacks.onImagePickerError(new IllegalStateException("Unable to get the picture returned from camera"), ImageSource.CAMERA_IMAGE, restoreType(activity));
            } else {
                if (configuration(activity).shouldCopyTakenPhotosToPublicGalleryAppFolder()) {
                    EasyImageFiles.copyFilesInSeparateThread(activity, EasyImageFiles.singleFileList(takenCameraPicture));
                }
                callbacks.onImagesPicked(arrayList, ImageSource.CAMERA_IMAGE, restoreType(activity));
            }
            PreferenceManager.getDefaultSharedPreferences(activity).edit().remove(KEY_LAST_CAMERA_PHOTO).remove(KEY_PHOTO_URI).apply();
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e, ImageSource.CAMERA_IMAGE, restoreType(activity));
        }
    }

    private static void onVideoReturnedFromCamera(Activity activity, @NonNull Callbacks callbacks) {
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(activity).getString(KEY_VIDEO_URI, (String) null);
            if (!TextUtils.isEmpty(string)) {
                revokeWritePermission(activity, Uri.parse(string));
            }
            File takenCameraVideo = takenCameraVideo(activity);
            List<File> arrayList = new ArrayList<>();
            arrayList.add(takenCameraVideo);
            if (takenCameraVideo == null) {
                callbacks.onImagePickerError(new IllegalStateException("Unable to get the video returned from camera"), ImageSource.CAMERA_VIDEO, restoreType(activity));
            } else {
                if (configuration(activity).shouldCopyTakenPhotosToPublicGalleryAppFolder()) {
                    EasyImageFiles.copyFilesInSeparateThread(activity, EasyImageFiles.singleFileList(takenCameraVideo));
                }
                callbacks.onImagesPicked(arrayList, ImageSource.CAMERA_VIDEO, restoreType(activity));
            }
            PreferenceManager.getDefaultSharedPreferences(activity).edit().remove(KEY_LAST_CAMERA_VIDEO).remove(KEY_VIDEO_URI).apply();
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e, ImageSource.CAMERA_VIDEO, restoreType(activity));
        }
    }

    public static void clearConfiguration(@NonNull Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove("pl.aprilapps.folder_name").remove("pl.aprilapps.easyimage.allow_multiple").remove("pl.aprilapps.easyimage.copy_taken_photos").remove("pl.aprilapps.easyimage.copy_picked_images").apply();
    }

    public static EasyImageConfiguration configuration(@NonNull Context context) {
        return new EasyImageConfiguration(context);
    }
}
