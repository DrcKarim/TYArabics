package com.BestofallHealthYoga.TamarinYoga.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.utils.Utils;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;
import com.BestofallHealthYoga.TamarinYoga.utils.Formula;

public class BmiCalculator_Fragment extends Fragment implements View.OnClickListener, TextWatcher {
    Button btnkgbmi;
    Button btnlbbmi;


    EditText heightEditText;
    TextView heightTextbmi;
    EditText heightinchedittext;
    TextView heightinchtextbmi;
    String kglbval;
    ImageView popupBMIButton;
    TextView resulthealthyweighttextView;
    TextView resultidealweighttextView;
    TextView resultoverweighttextView;
    SeekBar seekBar;
    Toolbar toolbar;
    TextView txtbmical;
    TextView txtbmivalue;
    EditText weightEditText;
    TextView weightTextbmi;

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.d("","ssd");
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.d("","ssd");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_bmicalculator, parent, false);


        this.weightEditText = (EditText) inflate.findViewById(R.id.weightEditTextbmi);
        this.heightEditText = (EditText) inflate.findViewById(R.id.heightEditTextbmi);
        this.weightEditText.addTextChangedListener(this);
        this.heightEditText.addTextChangedListener(this);
        this.heightinchedittext = (EditText) inflate.findViewById(R.id.height_inch_EditTextbmi);
        this.heightinchedittext.addTextChangedListener(this);
        this.resulthealthyweighttextView = (TextView) inflate.findViewById(R.id.result_healthyWeight_textView);
        this.resultoverweighttextView = (TextView) inflate.findViewById(R.id.result_overWeight_textView);
        this.resultidealweighttextView = (TextView) inflate.findViewById(R.id.result_idealWeight_textView);
        this.seekBar = (SeekBar) inflate.findViewById(R.id.seekBarIndicator);
        this.seekBar.setEnabled(false);
        this.weightTextbmi = (TextView) inflate.findViewById(R.id.weightTextbmi);
        this.heightTextbmi = (TextView) inflate.findViewById(R.id.heightTextbmi);
        this.heightinchtextbmi = (TextView) inflate.findViewById(R.id.height_inch_Textbmi);
        this.txtbmical = (TextView) inflate.findViewById(R.id.txtbmi_cal);
        this.txtbmivalue = (TextView) inflate.findViewById(R.id.txtbmi_value);
        this.popupBMIButton = (ImageView) inflate.findViewById(R.id.popupBMIButton);
        this.popupBMIButton.setOnClickListener(this);
        this.btnkgbmi = (Button) inflate.findViewById(R.id.btnKG_BMI);
        this.btnkgbmi.setOnClickListener(this);
        this.btnlbbmi = (Button) inflate.findViewById(R.id.btnLB_BMI);
        this.btnlbbmi.setOnClickListener(this);
        if (!AppPref.iskglbcalcu(this.getActivity())) {
            this.kglbval = " " + this.getActivity().getResources().getString(R.string.lb);
            this.heightinchedittext.setVisibility(View.VISIBLE);
            this.heightinchedittext.setText(String.valueOf(AppPref.getHeightinchcalculation(this.getActivity())));
            this.heightinchtextbmi.setVisibility(View.VISIBLE);
            this.heightinchtextbmi.setText(getString(R.string.in));
            this.heightTextbmi.setText("قدم");
            this.weightTextbmi.setText(this.kglbval);
        } else {
            this.weightEditText.setText(Formula.format((double) AppPref.getWeightcalculation(this.getActivity())));
            this.heightEditText.setText(String.valueOf(AppPref.getHeightcalculation(this.getActivity())));
        }
        setBtnBack();
        return inflate;


    }


    public void onClick(View view) {
        if (view.getId() == this.popupBMIButton.getId()) {
            PopupMenu popupMenu = new PopupMenu(this.getActivity(), view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_frag_bmi, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem menuItem) {
                    final Dialog dialog = new Dialog(BmiCalculator_Fragment.this.getActivity());
                    dialog.setContentView(R.layout.dialog_bmi_chart_table);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    ((Button) dialog.findViewById(R.id.buttonokbmi_table)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }
        if (view.getId() == this.btnkgbmi.getId()) {
            AppPref.setKglbcalculation(this.getActivity(), true);
            setBtnBack();
            this.heightinchedittext.setVisibility(View.GONE);
            this.heightinchtextbmi.setVisibility(View.GONE);
            this.weightEditText.setText("");
            this.heightEditText.setText("");
            this.heightinchedittext.setText("");
            this.heightTextbmi.setText("سم");
            this.weightTextbmi.setText(this.getActivity().getResources().getString(R.string.Kg));
            setTextViewDefault();
        }
        if (view.getId() == this.btnlbbmi.getId()) {
            this.weightTextbmi.setText(this.getActivity().getResources().getString(R.string.lb));
            AppPref.setKglbcalculation(this.getActivity(), false);
            this.heightinchedittext.setVisibility(View.VISIBLE);
            this.weightEditText.setText("");
            this.heightEditText.setText("");
            this.heightinchedittext.setText("");
            this.heightinchtextbmi.setVisibility(View.VISIBLE);
            this.heightinchtextbmi.setText(getString(R.string.in));
            this.heightTextbmi.setText("قدم");
            setTextViewDefault();
            setBtnBack();
        }
    }

    private void setBtnBack() {
        this.btnkgbmi.setBackground(AppPref.iskglbcalcu(this.getActivity()) ?
                getResources().getDrawable(R.drawable.a2_grad) :
                getResources().getDrawable(R.drawable.maleback));
        this.btnkgbmi.setTextColor(AppPref.iskglbcalcu(this.getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
        this.btnlbbmi.setTextColor(!AppPref.iskglbcalcu(this.getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
        this.btnlbbmi.setBackground(!AppPref.iskglbcalcu(this.getActivity()) ? getResources().getDrawable(R.drawable.a2_grad) : getResources().getDrawable(R.drawable.maleback));
    }

    public void afterTextChanged(Editable editable) {
        if (!bmiValidation()) {
            setTextViewDefault();
        } else {
            getBmiCalculation();
        }
    }

    private boolean bmiValidation() {
        if (AppPref.iskglbcalcu(this.getActivity()) && (this.weightEditText.getText().toString().isEmpty() || this.heightEditText.getText().toString().isEmpty())) {
            return false;
        }
        if (!AppPref.iskglbcalcu(this.getActivity()) && (this.weightEditText.getText().toString().isEmpty() || this.heightEditText.getText().toString().isEmpty() || this.heightinchedittext.getText().toString().isEmpty())) {
            Log.d("isKblb", "" + AppPref.iskglbcalcu(this.getActivity()));
            return false;
        } else if (Double.parseDouble(this.weightEditText.getText().toString()) == Utils.DOUBLE_EPSILON || Integer.parseInt(this.heightEditText.getText().toString()) == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void setTextViewDefault() {
        this.resultoverweighttextView.setText("-");
        this.resulthealthyweighttextView.setText("-");
        this.resultidealweighttextView.setText("-");
        this.txtbmical.setText("0");
        this.seekBar.setProgress(0);
        this.txtbmivalue.setText("");
    }

    public void getBmiCalculation() {
        double d;
        double d2;
        double d3;
        double parseFloat = (double) Float.parseFloat(this.weightEditText.getText().toString());
        int parseInt = Integer.parseInt(this.heightEditText.getText().toString());
        if (!AppPref.iskglbcalcu(this.getActivity())) {
            this.kglbval = " " + this.getActivity().getResources().getString(R.string.lb);
            int parseInt2 = Integer.parseInt(this.heightinchedittext.getText().toString());
            double convertFootToInch = Formula.convertFootToInch(parseInt);
            double d4 = (double) parseInt2;
            Double.isNaN(d4);
            double d5 = convertFootToInch + d4;
            d3 = Formula.getWeightFromBMIinLb(18.5d, d5);
            d2 = Formula.getWeightFromBMIinLb(24.9d, d5);
            AppPref.setHeightinchcalculation(this.getActivity(), Integer.parseInt(this.heightinchedittext.getText().toString()));
            d = Formula.bmicalculatorlb(parseFloat, d5);
        } else {
            this.kglbval = " " + this.getActivity().getResources().getString(R.string.Kg);
            double weightFromBMIinKg = Formula.getWeightFromBMIinKg(18.5d, Formula.convertCmtoMeter(parseInt));
            d2 = Formula.getWeightFromBMIinKg(24.9d, Formula.convertCmtoMeter(parseInt));
            d = Formula.bmicalculatorkg(parseFloat, Formula.convertCmtoMeter(parseInt));
            d3 = weightFromBMIinKg;
        }
        AppPref.setWeightcalculation(this.getActivity(), Float.parseFloat(this.weightEditText.getText().toString()));
        AppPref.setHeightcalculation(this.getActivity(), Integer.parseInt(this.heightEditText.getText().toString()));
        double idealWeight = Formula.getIdealWeight(d3, d2);
        double overWeight = Formula.getOverWeight(parseFloat, d2);
        if (overWeight < Utils.DOUBLE_EPSILON) {
            TextView textView = this.resultoverweighttextView;
            textView.setText("0" + this.kglbval);
        } else {
            TextView textView2 = this.resultoverweighttextView;
            textView2.setText(String.valueOf(Formula.format(overWeight)) + this.kglbval);
        }
        int i = 0;
        if (d >= 18.5d && d < 25.0d) {
            i = setProgresbar(18.5d, 25.0d, d, 20);
            this.txtbmivalue.setText(getString(R.string.normal));
            this.txtbmivalue.setTextColor(this.getActivity().getResources().getColor(R.color.color_range_orange));
        } else if (d >= 25.0d && d < 30.0d) {
            i = setProgresbar(25.0d, 30.0d, d, 40);
            this.txtbmivalue.setText(R.string.over_weight);
            this.txtbmivalue.setTextColor(this.getActivity().getResources().getColor(R.color.color_range_yellow));
        } else if (d >= 30.0d && d < 40.0d) {
            i = setProgresbar(30.0d, 40.0d, d, 60);
            this.txtbmivalue.setText(R.string.obese);
            this.txtbmivalue.setTextColor(this.getActivity().getResources().getColor(R.color.color_range_green));
        } else if (d >= 40.0d) {
            i = 90;
            this.txtbmivalue.setText(R.string.morbidly_obese);
            this.txtbmivalue.setTextColor(this.getActivity().getResources().getColor(R.color.color_range_blue));
        } else {
            this.txtbmivalue.setText("");
        }
        this.seekBar.setProgress(i);
        this.txtbmical.setText(Formula.format(d));
        TextView textView3 = this.resultidealweighttextView;
        textView3.setText(Formula.format(idealWeight) + this.kglbval);
        TextView textView4 = this.resulthealthyweighttextView;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(Formula.round(d3, 1) + " - " + Formula.round(d2, 1)));
        sb.append(this.kglbval);
        textView4.setText(sb.toString());
    }

    public static int setProgresbar(double d, double d2, double d3, int i) {
        double d4 = (double) i;
        Double.isNaN(d4);
        return (int) (d4 + (((((d3 - d) / (d2 - d)) * 100.0d) * 20.0d) / 100.0d));
    }


}
