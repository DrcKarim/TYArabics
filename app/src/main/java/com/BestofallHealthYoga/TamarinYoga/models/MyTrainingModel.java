package com.BestofallHealthYoga.TamarinYoga.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTrainingModel implements Parcelable {
    public static final Creator<MyTrainingModel> CREATOR = new Creator<MyTrainingModel>() {
        public MyTrainingModel createFromParcel(Parcel parcel) {
            return new MyTrainingModel(parcel);
        }

        public MyTrainingModel[] newArray(int i) {
            return new MyTrainingModel[i];
        }
    };
    int totalExercise;
    int typeid;
    String typename;

    public int describeContents() {
        return 0;
    }

    protected MyTrainingModel(Parcel parcel) {
        this.typeid = parcel.readInt();
        this.totalExercise = parcel.readInt();
        this.typename = parcel.readString();
    }

    public MyTrainingModel() {
    }

    public int getTypeid() {
        return this.typeid;
    }

    public void setTypeid(int i) {
        this.typeid = i;
    }

    public String getTypename() {
        return this.typename;
    }

    public void setTypename(String str) {
        this.typename = str;
    }

    public int getTotalExercise() {
        return this.totalExercise;
    }

    public void setTotalExercise(int i) {
        this.totalExercise = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.typeid);
        parcel.writeInt(this.totalExercise);
        parcel.writeString(this.typename);
    }
}
