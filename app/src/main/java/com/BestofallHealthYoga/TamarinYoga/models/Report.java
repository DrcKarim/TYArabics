package com.BestofallHealthYoga.TamarinYoga.models;

public class Report {
    long date;
    double weight;
    double weistline;

    public Report() {
    }

    public Report(long j, double d, double d2) {
        this.date = j;
        this.weight = d;
        this.weistline = d2;
    }

    public void setDate(long j) {
        this.date = j;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double d) {
        this.weight = d;
    }

    public double getWeistline() {
        return this.weistline;
    }

    public void setWeistline(double d) {
        this.weistline = d;
    }

    public long getDate() {
        return this.date;
    }
}
