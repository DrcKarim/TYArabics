package com.BestofallHealthYoga.TamarinYoga.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.dbhelper.BaseAppDB;
import com.BestofallHealthYoga.TamarinYoga.models.ChartData;
import com.BestofallHealthYoga.TamarinYoga.models.Report;
import com.BestofallHealthYoga.TamarinYoga.models.WeightStateModel;
import com.BestofallHealthYoga.TamarinYoga.utils.AdGlobal;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import com.BestofallHealthYoga.TamarinYoga.utils.BaseApp;
import com.BestofallHealthYoga.TamarinYoga.utils.Constant;
import com.BestofallHealthYoga.TamarinYoga.utils.MyAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ReportsActivity extends Fragment implements View.OnClickListener {
    Calendar calendar;
    CardView cardWeight;
    CardView cardWeistlne;
    DatePickerDialog datePickerDialog;
    Dialog dialog;
    EditText etdata;
    TextView etdate;
    FrameLayout frameLayout;
    List<Report> getTempReportDataModels;
    ImageView imgminus;
    ImageView imgplus;
    ImageView imgweight;
    ImageView imgweistline;
    boolean isEdit = false;
    LinkedHashMap<Integer, ChartData> linkedHashMapwaistLine;
    LinkedHashMap<Integer, ChartData> linkedWeightHashMap;
    BaseAppDB myDb;
    TextView name;
    ArrayList<Report> reportArrayList;
    TextView title;
    Toolbar toolbar;
    TextView txtCurrentWaist;
    TextView txtHeavyWaist;
    TextView txtHeavyWeight;
    TextView txtLightWaist;
    TextView txtLightWeight;
    TextView txtcurrentWeight;
    int type;
    LineChart waistLineChart;
    List<ChartData> waistLineList;
    LineChart weightChart;
    TextView weightCount;
    TextView weightDate;
    Report weightReport;
    List<ChartData> weightlist;
    TextView weistLineCount;
    TextView weistLineDate;
    Report weistlineReport;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_reports, parent, false);

        this.imgweight = (ImageView) inflate.findViewById(R.id.img_weight);
        this.imgweight.setOnClickListener(this);
        this.imgweistline = (ImageView) inflate.findViewById(R.id.img_weistline);
        this.imgweistline.setOnClickListener(this);
        this.weightChart = (LineChart) inflate.findViewById(R.id.weightChart);
        this.waistLineChart = (LineChart) inflate.findViewById(R.id.waistLineChart);
        this.txtcurrentWeight = (TextView) inflate.findViewById(R.id.txtcurrentWeight);
        this.txtHeavyWeight = (TextView) inflate.findViewById(R.id.txtHeavyWeight);
        this.txtLightWeight = (TextView) inflate.findViewById(R.id.txtLightWeight);
        this.txtCurrentWaist = (TextView) inflate.findViewById(R.id.txtCurrentWaist);
        this.txtHeavyWaist = (TextView) inflate.findViewById(R.id.txtHeavyWaist);
        this.txtLightWaist = (TextView) inflate.findViewById(R.id.txtLightWaist);
        this.weightCount = (TextView) inflate.findViewById(R.id.weightCount);
        this.weightDate = (TextView) inflate.findViewById(R.id.weighteDate);
        this.weistLineCount = (TextView) inflate.findViewById(R.id.WeistLineCount);
        this.weistLineDate = (TextView) inflate.findViewById(R.id.weistLineDate);
        this.cardWeight = (CardView) inflate.findViewById(R.id.cardWeight);
        this.cardWeistlne = (CardView) inflate.findViewById(R.id.cardWeistLine);
        this.linkedWeightHashMap = new LinkedHashMap<>();
        this.linkedHashMapwaistLine = new LinkedHashMap<>();
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.fl_adplaceholder);
        this.cardWeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReportsActivity reportsActivity = ReportsActivity.this;
                reportsActivity.type = 1;
                reportsActivity.openDialog(getString(R.string.weight));
            }
        });
        this.cardWeistlne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReportsActivity reportsActivity = ReportsActivity.this;
                reportsActivity.type = 2;
                reportsActivity.openDialog(getString(R.string.waistline));
            }
        });
        this.getTempReportDataModels = new ArrayList<>();
        this.myDb = new BaseAppDB(getActivity());
        getData();

        setNativeLayout();

        return inflate;
    }


    private void getData() {
        this.weightReport = this.myDb.getRepostWeightWaistetLine(1);
        Report report = this.weightReport;
        if (report != null) {
            if (report.getDate() == 0) {
                this.weightDate.setText(getString(R.string.tap_to_record_value));
                this.weightCount.setText("");
            } else {
                this.weightDate.setText(Constant.getFormattedDate(this.weightReport.getDate(), new SimpleDateFormat("MMM dd,yyyy")));
                TextView textView = this.weightCount;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(this.weightReport.getWeight()));
                sb.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : " lb");
                textView.setText(sb.toString());
            }
        }
        this.weistlineReport = this.myDb.getRepostWeightWaistetLine(2);
        Report report2 = this.weistlineReport;
        if (report2 != null) {
            if (report2.getDate() == 0) {
                this.weistLineDate.setText(getString(R.string.tap_to_record_value));
                this.weistLineCount.setText("");
            } else {
                this.weistLineDate.setText(Constant.getFormattedDate(this.weistlineReport.getDate(),  new SimpleDateFormat("MMM dd,yyyy")));
                TextView textView2 = this.weistLineCount;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(String.valueOf(this.weistlineReport.getWeistline()));
                sb2.append(AppPref.isMatrixWaist(BaseApp.getInstance()) ? " cm" : " in");
                textView2.setText(sb2.toString());
            }
        }
        reightreport();
        waistLinechart();
        setReportStates();
    }

    private void setReportStates() {
        WeightStateModel weightStates = this.myDb.getWeightStates("weight");
        TextView textView = this.txtcurrentWeight;
        StringBuilder sb = new StringBuilder();
        sb.append(weightStates.getCurrent());
        sb.append(" ");
        sb.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : "lb");
        textView.setText(sb.toString());
        TextView textView2 = this.txtHeavyWeight;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(weightStates.getMax());
        sb2.append(" ");
        sb2.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : "lb");
        textView2.setText(sb2.toString());
        TextView textView3 = this.txtLightWeight;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(weightStates.getMin());
        sb3.append(" ");
        sb3.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : "lb");
        textView3.setText(sb3.toString());
        WeightStateModel weightStates2 = this.myDb.getWeightStates("waistline");
        TextView textView4 = this.txtCurrentWaist;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(weightStates2.getCurrent());
        sb4.append(" ");
        sb4.append(AppPref.isMatrixWaist(BaseApp.getInstance()) ? " cm" : "in");
        textView4.setText(sb4.toString());
        TextView textView5 = this.txtHeavyWaist;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(weightStates2.getMax());
        sb5.append(" ");
        sb5.append(AppPref.isMatrixWaist(BaseApp.getInstance()) ? " cm" : "in");
        textView5.setText(sb5.toString());
        TextView textView6 = this.txtLightWaist;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(weightStates2.getMin());
        sb6.append(" ");
        sb6.append(AppPref.isMatrixWaist(BaseApp.getInstance()) ? " cm" : "in");
        textView6.setText(sb6.toString());
    }


    @SuppressLint({"NewApi"})
    public void openDialog(String str) {
        this.dialog = new Dialog(getActivity());
        this.dialog.setContentView(R.layout.add_data_dialog);
        this.dialog.setCancelable(true);
        Window window = this.dialog.getWindow();
        window.setLayout(-1, -2);
        window.setGravity(17);
        this.dialog.getWindow().setBackgroundDrawableResource(17170445);
        this.dialog.show();
        FloatingActionButton floatingActionButton = (FloatingActionButton) this.dialog.findViewById(R.id.fabClose);
        Button button = (Button) this.dialog.findViewById(R.id.btnSave);
        this.etdate = (TextView) this.dialog.findViewById(R.id.et_date);
        this.etdata = (EditText) this.dialog.findViewById(R.id.et_data);
        this.title = (TextView) this.dialog.findViewById(R.id.title);
        this.name = (TextView) this.dialog.findViewById(R.id.name);
        this.name.setText(str);
        this.imgplus = (ImageView) this.dialog.findViewById(R.id.img_plus);
        this.imgminus = (ImageView) this.dialog.findViewById(R.id.img_minus);
        final double[] dArr = {0.0d};
        this.imgplus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                if (ReportsActivity.this.etdata.getText().toString().trim().isEmpty()) {
                    dArr[0] = dArr[0] + 1.0d;
                    ReportsActivity.this.etdata.setText(String.valueOf(Constant.decimalFormat.format(dArr[0])));
                    return;
                }
                dArr[0] = Double.parseDouble(ReportsActivity.this.etdata.getText().toString());
                double[] dArr2 = dArr;
                dArr2[0] = dArr2[0] + 1.0d;
                ReportsActivity.this.etdata.setText(String.valueOf(Constant.decimalFormat.format(dArr[0])));
            }
        });
        this.imgminus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                dArr[0] = Double.parseDouble(ReportsActivity.this.etdata.getText().toString());

                if (dArr[0] > 1.0d) {
                    dArr[0] = dArr[0] - 1.0d;
                    ReportsActivity.this.etdata.setText(String.valueOf(Constant.decimalFormat.format(dArr[0])));
                }
            }
        });
        if (this.type == 1) {
            this.title.setText(R.string.weight);
            if (this.weightReport != null) {
                EditText editText = this.etdata;
                editText.setText(this.weightReport.getWeight() + "");
            }
        } else {
            this.title.setText(R.string.waistline);
            if (this.weistlineReport != null) {
                EditText editText2 = this.etdata;
                editText2.setText(this.weistlineReport.getWeistline() + "");
            }
        }
        this.calendar = Calendar.getInstance();
        this.etdate.setText(Constant.getFormattedDate(this.calendar.getTimeInMillis(), new SimpleDateFormat("MMM dd,yyyy")));
        this.etdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReportsActivity.this.openCalendar();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                if (!ReportsActivity.this.etdata.getText().toString().trim().isEmpty()) {
                    try {
                        dArr[0] = Double.parseDouble(ReportsActivity.this.etdata.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), getString(R.string.enter_valid_value), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    ReportsActivity reportsActivity = ReportsActivity.this;
                    reportsActivity.saveData(reportsActivity.type, ReportsActivity.this.calendar.getTimeInMillis(), dArr[0]);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.enter_value), Toast.LENGTH_SHORT).show();
                }
                ReportsActivity.this.dialog.dismiss();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ReportsActivity.this.dialog.dismiss();
            }
        });
    }


    public void saveData(int i, long j, double d) {
        getBasicData(Constant.getTimeinMilliWithoutTime(this.calendar.getTimeInMillis()));
        switch (i) {

            case 1:
                this.weightReport.setWeight(d);
                this.weightReport.setDate(Constant.getTimeinMilliWithoutTime(j));
                saveUpdate(i, this.weightReport.getDate(), this.weightReport.getWeight());
                break;
            case 2:
                this.weistlineReport.setWeistline(d);
                this.weistlineReport.setDate(Constant.getTimeinMilliWithoutTime(j));
                saveUpdate(i, this.weistlineReport.getDate(), this.weistlineReport.getWeistline());

                break;
            default:
                break;
        }

        getData();


    }

    private void saveUpdate(int i, long j, double d) {
        if (!this.isEdit) {
            this.myDb.addReportData(i, j, d);
        } else {
            this.myDb.updateReportData(i, j, d);
        }
    }


    @SuppressLint({"NewApi"})
    public void openCalendar() {
        this.calendar = Calendar.getInstance();
        int i = this.calendar.get(1);
        int i2 = this.calendar.get(2);
        int i3 = this.calendar.get(5);
        DatePickerDialog datePickerDialog2 = this.datePickerDialog;
        if (datePickerDialog2 != null && datePickerDialog2.isShowing()) {
            this.datePickerDialog.dismiss();
        }
        this.datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @SuppressLint({"NewApi"})
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                ReportsActivity.this.calendar.set(5, i3);
                ReportsActivity.this.calendar.set(2, i2);
                ReportsActivity.this.calendar.set(1, i);
                ReportsActivity.this.etdate.setText(Constant.getFormattedDate(ReportsActivity.this.calendar.getTimeInMillis(),  new SimpleDateFormat("MMM dd,yyyy")));
            }
        }, i, i2, i3);
        this.datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        this.datePickerDialog.show();
    }

    public void getBasicData(long j) {
        this.isEdit = false;
        this.getTempReportDataModels = this.myDb.getReportData(j);
        if (!this.getTempReportDataModels.isEmpty()) {
            this.isEdit = true;
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_weight:
                startActivityForResult(new Intent(getActivity(), SummaryActivity.class).putExtra("TYPE_WEIGHT", false), 560);
                return;
            case R.id.img_weistline:
                startActivityForResult(new Intent(getActivity(), SummaryActivity.class).putExtra("TYPE_WEIGHT", true), 560);
                return;
            default:
                return;
        }
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 560 && intent != null && intent.getBooleanExtra("isDelete", false)) {
            getData();
        }
    }

    public void reightreport() {
        this.weightChart.getAxisRight().removeAllLimitLines();
        this.weightChart.getAxisLeft().removeAllLimitLines();
        this.weightChart.clear();
        this.weightChart.getAxisLeft().resetAxisMaximum();
        this.weightChart.getAxisLeft().resetAxisMinimum();
        this.weightChart.getAxisRight().resetAxisMaximum();
        this.weightChart.getAxisRight().resetAxisMinimum();
        this.weightChart.setDrawGridBackground(false);
        this.weightChart.getDescription().setEnabled(false);
        this.weightChart.getLegend().setEnabled(false);
        this.weightChart.getAxisLeft().setDrawAxisLine(false);
        this.weightChart.getAxisRight().setDrawAxisLine(false);
        this.weightChart.getAxisLeft().setTextColor(R.color.white);
        this.weightChart.getXAxis().setTextColor(R.color.white);
        this.weightChart.getLegend().setTextColor(R.color.white);
        this.weightChart.setNoDataTextColor(R.color.chartHeaderColor);
        this.weightChart.setTouchEnabled(true);
        this.weightChart.setPinchZoom(true);
        this.weightChart.getXAxis().enableGridDashedLine(10.0f, 10.0f, 0.0f);
        this.weightChart.getAxisRight().setEnabled(false);
        this.weightlist = this.myDb.getAllWeight("weight");
        if (!this.weightlist.isEmpty()) {

            List<Entry> arrayList = new ArrayList<>();
            for (int i = 0; i < this.weightlist.size(); i++) {
                this.linkedWeightHashMap.put(Integer.valueOf(i), this.weightlist.get(i));
                arrayList.add(new Entry((float) i, (float) this.weightlist.get(i).getMeasurementWeight()));
            }
            LineDataSet lineDataSet = new LineDataSet(arrayList, "Weight");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setDrawIcons(false);
            lineDataSet.enableDashedLine(0.0f, 0.0f, 0.0f);
            lineDataSet.enableDashedHighlightLine(10.0f, 5.0f, 0.0f);
            lineDataSet.setColor(R.color.endcolor);
            lineDataSet.setCircleColor(R.color.endcolor);
            lineDataSet.setLineWidth(2.0f);
            lineDataSet.setCircleRadius(3.0f);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1.0f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10.0f, 5.0f}, 0.0f));
            lineDataSet.setFormSize(15.0f);
            lineDataSet.setDrawValues(true);
            lineDataSet.setValueTextSize(11.0f);
            if (Build.VERSION.SDK_INT < 18) {
                lineDataSet.setFillColor(R.color.endcolor);
            }

            List<ILineDataSet> arrayList2 = new ArrayList<>();
            arrayList2.add(lineDataSet);
            LineData lineData = new LineData(arrayList2);
            lineData.setValueFormatter(new MyValueFormatterDec());
            this.weightChart.moveViewToX((float) arrayList.size());
            this.weightChart.fitScreen();
            this.weightChart.setData(lineData);
            this.weightChart.invalidate();
            commonreportdata();
        }
    }

    public void commonreportdata() {
        if (!this.weightlist.isEmpty() && this.weightChart.getData() != null) {
            XAxis xAxis = this.weightChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new MyAxisValueFormatter(this.linkedWeightHashMap));
            xAxis.setGranularityEnabled(true);
            this.weightChart.setVisibleXRangeMaximum(10.0f);
            this.weightChart.moveViewToX(5.0f);
            try {
                ((LineData) this.weightChart.getData()).getYMin();
                float yMax = ((LineData) this.weightChart.getData()).getYMax();
                YAxis axisLeft = this.weightChart.getAxisLeft();
                axisLeft.setEnabled(true);
                axisLeft.setDrawGridLines(true);
                axisLeft.setDrawAxisLine(false);
                axisLeft.setAxisMinimum(0.0f);
                axisLeft.setAxisMaximum(yMax + 10.0f);
                axisLeft.setValueFormatter(new IAxisValueFormatter() {
                    public String getFormattedValue(float f, AxisBase axisBase) {
                        StringBuilder sb = new StringBuilder();
                        sb.append((int) f);
                        sb.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " kg" : " lb");
                        return sb.toString();
                    }
                });
                axisLeft.setDrawZeroLine(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void waistLinechart() {
        this.waistLineChart.getAxisRight().removeAllLimitLines();
        this.waistLineChart.getAxisLeft().removeAllLimitLines();
        this.waistLineChart.clear();
        this.waistLineChart.getAxisLeft().resetAxisMaximum();
        this.waistLineChart.getAxisLeft().resetAxisMinimum();
        this.waistLineChart.getAxisRight().resetAxisMaximum();
        this.waistLineChart.getAxisRight().resetAxisMinimum();
        this.waistLineChart.setDrawGridBackground(false);
        this.waistLineChart.getDescription().setEnabled(false);
        this.waistLineChart.getLegend().setEnabled(false);
        this.waistLineChart.getAxisLeft().setDrawAxisLine(false);
        this.waistLineChart.getAxisRight().setDrawAxisLine(false);
        this.waistLineChart.getAxisLeft().setTextColor(R.color.charttextcolor);
        this.waistLineChart.getXAxis().setTextColor(R.color.charttextcolor);
        this.waistLineChart.getLegend().setTextColor(R.color.charttextcolor);
        this.waistLineChart.setNoDataTextColor(R.color.chartHeaderColor);
        this.waistLineChart.setTouchEnabled(true);
        this.waistLineChart.setPinchZoom(true);
        this.waistLineChart.getXAxis().enableGridDashedLine(10.0f, 10.0f, 0.0f);
        this.waistLineChart.getAxisRight().setEnabled(false);
        this.waistLineList = this.myDb.getAllWeight("waistline");
        if (!this.waistLineList.isEmpty()) {

            List<Entry> arrayList = new ArrayList<>();
            for (int i = 0; i < this.waistLineList.size(); i++) {
                this.linkedHashMapwaistLine.put(Integer.valueOf(i), this.waistLineList.get(i));
                arrayList.add(new Entry((float) i, (float) this.waistLineList.get(i).getMeasurementWeight()));
            }
            LineDataSet lineDataSet = new LineDataSet(arrayList, "Waistline");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setDrawIcons(false);
            lineDataSet.enableDashedLine(0.0f, 0.0f, 0.0f);
            lineDataSet.enableDashedHighlightLine(10.0f, 5.0f, 0.0f);
            lineDataSet.setColor(R.color.endcolor);
            lineDataSet.setCircleColor(R.color.endcolor);
            lineDataSet.setLineWidth(2.0f);
            lineDataSet.setCircleRadius(3.0f);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1.0f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10.0f, 5.0f}, 0.0f));
            lineDataSet.setFormSize(15.0f);
            lineDataSet.setDrawValues(true);
            lineDataSet.setValueTextSize(11.0f);
            if (Build.VERSION.SDK_INT < 18) {
                lineDataSet.setFillColor(R.color.endcolor);
            }
            List<ILineDataSet> arrayList2 = new ArrayList<>();
            arrayList2.add(lineDataSet);
            LineData lineData = new LineData(arrayList2);


            lineData.setValueFormatter(new MyValueFormatterDec());
            this.waistLineChart.moveViewToX((float) arrayList.size());
            this.waistLineChart.fitScreen();
            this.waistLineChart.setData(lineData);
            this.waistLineChart.invalidate();
            if (!this.waistLineList.isEmpty() && this.waistLineChart.getData() != null) {
                XAxis xAxis = this.waistLineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new MyAxisValueFormatter(this.linkedHashMapwaistLine));
                xAxis.setGranularityEnabled(true);
                this.waistLineChart.setVisibleXRangeMaximum(10.0f);
                this.waistLineChart.moveViewToX(5.0f);
                try {
                    ((LineData) this.waistLineChart.getData()).getYMin();
                    float yMax = ((LineData) this.waistLineChart.getData()).getYMax();
                    YAxis axisLeft = this.waistLineChart.getAxisLeft();
                    axisLeft.setEnabled(true);
                    axisLeft.setDrawGridLines(true);
                    axisLeft.setDrawAxisLine(false);
                    axisLeft.setAxisMinimum(0.0f);
                    axisLeft.setAxisMaximum(yMax + 10.0f);
                    axisLeft.setValueFormatter(new IAxisValueFormatter() {
                        public String getFormattedValue(float f, AxisBase axisBase) {
                            StringBuilder sb = new StringBuilder();
                            sb.append((int) f);
                            sb.append(AppPref.isMatrixWeight(BaseApp.getInstance()) ? " cm" : " in");
                            return sb.toString();
                        }
                    });
                    axisLeft.setDrawZeroLine(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class MyValueFormatterDec implements IValueFormatter {
        public MyValueFormatterDec() {
            Log.d("sds","hello");
        }

        public String getFormattedValue(float f, Entry entry, int i, ViewPortHandler viewPortHandler) {
            double d = (double) f;
            if (d <= Utils.DOUBLE_EPSILON) {
                return "";
            }
            DecimalFormatSymbols instance = DecimalFormatSymbols.getInstance(Locale.US);
            return "" + new DecimalFormat("######.##", instance).format(d);
        }
    }

    public void setNativeLayout() {
        if (MainActivity.nativeAd != null) {
            try {
                UnifiedNativeAdView unifiedNativeAdView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_admob_native_large, (ViewGroup) null);
                AdGlobal.populateLarge(MainActivity.nativeAd, unifiedNativeAdView);
                this.frameLayout.removeAllViews();
                this.frameLayout.addView(unifiedNativeAdView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
