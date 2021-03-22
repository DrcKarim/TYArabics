package com.BestofallHealthYoga.TamarinYoga.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.BestofallHealthYoga.TamarinYoga.models.ChartData;


import java.util.Map;

public class MyAxisValueFormatter implements IAxisValueFormatter {

    Map<Integer, ChartData> monthList;

    public MyAxisValueFormatter(Map<Integer, ChartData> linkedHashMap) {
        this.monthList = linkedHashMap;
    }

    public String getFormattedValue(float f, AxisBase axisBase) {
       Map<Integer, ChartData> linkedHashMap = this.monthList;
        if (linkedHashMap == null) {
            return "";
        }
        int i = (int) f;
        return linkedHashMap.get(Integer.valueOf(i)) == null ? "" : this.monthList.get(Integer.valueOf(i)).getTimemille();
    }
}
