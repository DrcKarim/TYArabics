package com.BestofallHealthYoga.TamarinYoga.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseInformation implements Parcelable {
    public static final Creator<ExerciseInformation> CREATOR = new Creator<ExerciseInformation>() {
        public ExerciseInformation createFromParcel(Parcel parcel) {
            return new ExerciseInformation(parcel);
        }

        public ExerciseInformation[] newArray(int i) {
            return new ExerciseInformation[i];
        }
    };
    int activityId;
    String exerciseInformation;
    String exerciseName;

    public int describeContents() {
        return 0;
    }

    protected ExerciseInformation(Parcel parcel) {
        this.exerciseName = parcel.readString();
        this.activityId = parcel.readInt();
        this.exerciseInformation = parcel.readString();
    }

    public ExerciseInformation() {
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public void setExerciseName(String str) {
        this.exerciseName = str;
    }

    public int getActivityId() {
        return this.activityId;
    }

    public void setActivityId(int i) {
        this.activityId = i;
    }

    public String getExerciseInformation() {
        return this.exerciseInformation;
    }

    public void setExerciseInformation(String str) {
        this.exerciseInformation = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.exerciseName);
        parcel.writeInt(this.activityId);
        parcel.writeString(this.exerciseInformation);
    }
}
