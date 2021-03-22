package com.BestofallHealthYoga.TamarinYoga.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DayWiseActivity implements Parcelable {
    public static final Creator<DayWiseActivity> CREATOR = new Creator<DayWiseActivity>() {
        public DayWiseActivity createFromParcel(Parcel parcel) {
            return new DayWiseActivity(parcel);
        }

        public DayWiseActivity[] newArray(int i) {
            return new DayWiseActivity[i];
        }
    };
    String activityInformation;
    String activityName;
    int activityid;
    int actulpos;
    int dayid;
    int finishActivity;
    int id;
    long intervalTime;
    int ison;
    int orderofactivity;
    String tips;
    int totalset;
    int typeid;

    public int describeContents() {
        return 0;
    }

    public DayWiseActivity() {
    }

    protected DayWiseActivity(Parcel parcel) {
        this.id = parcel.readInt();
        this.dayid = parcel.readInt();
        this.activityid = parcel.readInt();
        this.typeid = parcel.readInt();
        this.totalset = parcel.readInt();
        this.orderofactivity = parcel.readInt();
        this.finishActivity = parcel.readInt();
        this.activityName = parcel.readString();
        this.intervalTime = parcel.readLong();
        this.actulpos = parcel.readInt();
        this.ison = parcel.readInt();
        this.activityInformation = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.dayid);
        parcel.writeInt(this.activityid);
        parcel.writeInt(this.typeid);
        parcel.writeInt(this.totalset);
        parcel.writeInt(this.orderofactivity);
        parcel.writeInt(this.finishActivity);
        parcel.writeString(this.activityName);
        parcel.writeLong(this.intervalTime);
        parcel.writeInt(this.actulpos);
        parcel.writeInt(this.ison);
        parcel.writeString(this.activityInformation);
    }

    public int getDayid() {
        return this.dayid;
    }

    public void setDayid(int i) {
        this.dayid = i;
    }

    public int getActivityid() {
        return this.activityid;
    }

    public void setActivityid(int i) {
        this.activityid = i;
    }

    public int getTotalset() {
        return this.totalset;
    }

    public void setTotalset(int i) {
        this.totalset = i;
    }

    public int getOrderofactivity() {
        return orderofactivity;
    }

    public void setOrderofactivity(int orderofactivity) {
        this.orderofactivity = orderofactivity;
    }



    public int getIsFinishActivity() {
        return this.finishActivity;
    }

    public void setIsFinishActivity(int i) {
        this.finishActivity = i;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String str) {
        this.activityName = str;
    }

    public long getIntervalTime() {
        return this.intervalTime;
    }

    public void setIntervalTime(long j) {
        this.intervalTime = j;
    }

    public int getActulpos() {
        return this.actulpos;
    }

    public void setActulpos(int i) {
        this.actulpos = i;
    }

    public int getTypeid() {
        return this.typeid;
    }

    public void setTypeid(int i) {
        this.typeid = i;
    }

    public int getIson() {
        return this.ison;
    }

    public void setIson(int i) {
        this.ison = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getActivityInformation() {
        return this.activityInformation;
    }

    public void setActivityInformation(String str) {
        this.activityInformation = str;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String str) {
        this.tips = str;
    }
}
