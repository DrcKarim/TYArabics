package com.BestofallHealthYoga.TamarinYoga.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserExercise implements Parcelable {
    public static final Creator<UserExercise> CREATOR = new Creator<UserExercise>() {
        public UserExercise createFromParcel(Parcel parcel) {
            return new UserExercise(parcel);
        }

        public UserExercise[] newArray(int i) {
            return new UserExercise[i];
        }
    };
    int activityId;
    String exerciseName;
    int totalTime = 10;

    public int describeContents() {
        return 0;
    }

    protected UserExercise(Parcel parcel) {
        this.exerciseName = parcel.readString();
        this.totalTime = parcel.readInt();
        this.activityId = parcel.readInt();
    }

    public UserExercise() {
    }

    public UserExercise(UserExercise userExercise) {
        this.exerciseName = userExercise.exerciseName;
        this.totalTime = userExercise.totalTime;
        this.activityId = userExercise.activityId;
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public void setExerciseName(String str) {
        this.exerciseName = str;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(int i) {
        this.totalTime = i;
    }

    public int getActivityId() {
        return this.activityId;
    }

    public void setActivityId(int i) {
        this.activityId = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.exerciseName);
        parcel.writeInt(this.totalTime);
        parcel.writeInt(this.activityId);
    }
}
