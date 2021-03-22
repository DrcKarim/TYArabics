package com.BestofallHealthYoga.TamarinYoga.babyphotos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BabyPhotos implements Parcelable {
    public static final Creator<BabyPhotos> CREATOR = new Creator<BabyPhotos>() {
        public BabyPhotos createFromParcel(Parcel parcel) {
            return new BabyPhotos(parcel);
        }

        public BabyPhotos[] newArray(int i) {
            return new BabyPhotos[i];
        }
    };
    @NonNull
    String id;
    String imageName;
    String title;

    public int describeContents() {
        return 0;
    }

    public BabyPhotos(@NonNull String str, String str2, String str3) {
        this.id = str;
        this.title = str2;
        this.imageName = str3;
    }

    public BabyPhotos() {

    }

    protected BabyPhotos(Parcel parcel) {
        this.id = parcel.readString();
        this.title = parcel.readString();
        this.imageName = parcel.readString();
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public void setId(@NonNull String str) {
        this.id = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String str) {
        this.imageName = str;
    }

    public boolean equals(Object obj) {

        if (obj == null || !BabyPhotos.class.isAssignableFrom(obj.getClass()) || !this.id.equals(((BabyPhotos) obj).id)) {
            return false;
        }
        return true;

    }


    public int hashCode() {

        return Integer.parseInt(this.id);
    }


    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.imageName);
    }
}
