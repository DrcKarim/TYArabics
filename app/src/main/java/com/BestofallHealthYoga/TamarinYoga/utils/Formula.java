package com.BestofallHealthYoga.TamarinYoga.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Formula {



    public static double bmicalculatorkg(double d, double d2) {
        return d / (d2 * d2);
    }

    public static double bmicalculatorlb(double d, double d2) {
        return (d / (d2 * d2)) * 703.0d;
    }

    public static double convertFootToInch(int i) {
        return (double) (i * 12);
    }

    public static double getIdealWeight(double d, double d2) {
        return d + ((d2 - d) / 2.0d);
    }

    public static double getWeightFromBMIinKg(double d, double d2) {
        return d * d2 * d2;
    }

    public static double getWeightFromBMIinLb(double d, double d2) {
        return (d * (d2 * d2)) / 703.0d;
    }

    public static double convertCmtoMeter(int i) {
        double d = (double) i;
        Double.isNaN(d);
        return d * 0.01d;
    }

    public static double getOverWeight(double d, double d2) {
        return round(d - d2, 1);
    }

    public static double round(double d, int i) {
        if (i >= 0) {
            return BigDecimal.valueOf(d).setScale(i, RoundingMode.HALF_UP).doubleValue();
        }
        throw new IllegalArgumentException();
    }

    public static String format(double d) {
        return new DecimalFormat("##.#").format(d);
    }
}
