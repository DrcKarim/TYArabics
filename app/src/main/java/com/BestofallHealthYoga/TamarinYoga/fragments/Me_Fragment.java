package com.BestofallHealthYoga.TamarinYoga.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.BestofallHealthYoga.TamarinYoga.BuildConfig;
import com.BestofallHealthYoga.TamarinYoga.R;
import com.BestofallHealthYoga.TamarinYoga.activities.Reminder;
import com.BestofallHealthYoga.TamarinYoga.utils.AppPref;

public class Me_Fragment extends Fragment {

    LinearLayout lay1;
    LinearLayout lay2;
    LinearLayout lay3;
    LinearLayout lay4;
    LinearLayout lay5;
    String title = "كيف كانت تجربتك معنا؟";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.me_fragment, parent, false);

        String playStoreUrl = "https://play.google.com/store/apps/details?id=" + getContext().getPackageName();


        lay1 = (LinearLayout) inflate.findViewById(R.id.lay1);
        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matrixSystem();

            }
        });

        lay2 = (LinearLayout) inflate.findViewById(R.id.lay2);
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(getActivity(), Reminder.class);
                startActivity(intent6);
            }
        });

        lay3 = (LinearLayout) inflate.findViewById(R.id.lay3);
        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
                RatingDialog build = new RatingDialog.Builder(getActivity()).session(1).threshold(4.0f).title(title).icon(getResources().getDrawable(R.mipmap.logo)).titleTextColor(R.color.colorPrimary).negativeButtonText("أبدا").positiveButtonTextColor(R.color.colorPrimary).negativeButtonTextColor(R.color.colorPrimary).feedbackTextColor(R.color.colorPrimary).formTitle("إرسال التعليقات").formHint("أخبرنا أين يمكننا أن نتطور").formSubmitText("إرسال").formCancelText("إلغاء").ratingBarColor(R.color.ratingBarColor).ratingBarBackgroundColor(R.color.ratingBarBackgroundColor).playstoreUrl(playStoreUrl).onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    public void onFormSubmitted(String str) {
                        emailus(str);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putBoolean("shownever", true);
                        edit.commit();
                    }
                }).build();
                if (sharedPreferences.getBoolean("shownever", false)) {
                    Toast.makeText(getActivity(), "أرسلت بالفعل", Toast.LENGTH_SHORT).show();
                } else {
                    build.show();
                }
            }
        });

        lay4 = (LinearLayout) inflate.findViewById(R.id.lay4);
        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent5 = new Intent("android.intent.action.SEND");
                intent5.setType("text/plain");
                intent5.putExtra("android.intent.extra.TEXT" , playStoreUrl);
                startActivityForResult(Intent.createChooser(intent5, "شارك عبر"), 9);

            }
        });

        lay5 = (LinearLayout) inflate.findViewById(R.id.lay5);
        lay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://1bestofall.com/%d8%b3%d9%8a%d8%a7%d8%b3%d8%a9-%d8%ae%d8%a7%d8%b5%d8%a9/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        return inflate;
    }

    public void matrixSystem() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_matrix_system);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(-1, -2);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.show();
        final TextView textView = (TextView) dialog.findViewById(R.id.kg);
        final TextView textView2 = (TextView) dialog.findViewById(R.id.lbs);
        final TextView textView3 = (TextView) dialog.findViewById(R.id.cm);
        final TextView textView4 = (TextView) dialog.findViewById(R.id.in);
        textView.setSelected(AppPref.isMatrixWeight(getActivity()));
        textView2.setSelected(!AppPref.isMatrixWeight(getActivity()));
        textView3.setSelected(AppPref.isMatrixWaist(getActivity()));
        textView4.setSelected(true ^ AppPref.isMatrixWaist(getActivity()));
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                textView.setSelected(true);
                textView2.setSelected(false);
                AppPref.setMatrixWeight(getActivity(), true);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                textView.setSelected(false);
                textView2.setSelected(true);
                AppPref.setMatrixWeight(getActivity(), false);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                textView3.setSelected(true);
                textView4.setSelected(false);
                AppPref.setMatrixWaist(getActivity(), true);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                textView3.setSelected(false);
                textView4.setSelected(true);
                AppPref.setMatrixWaist(getActivity(), false);
            }
        });
    }


    public void emailus(String str) {
        try {
            String str2 = Build.MODEL;
            String str3 = Build.MANUFACTURER;
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("Abc@gmail.com"));
            intent.putExtra("android.intent.extra.SUBJECT", "سيكون اقتراحك ممتنًا - تمارين اليوغا اليومية )" + getActivity().getPackageName() + ")");
            intent.putExtra("android.intent.extra.TEXT", str + "\n\nالشركة المصنعة للجهاز : " + str3 + "\nطراز الجهاز : " + str2 + "\nنسخة أندرويد : " + Build.VERSION.RELEASE + "\nنسخة التطبيق : " + BuildConfig.VERSION_NAME);
            startActivityForResult(intent, 9);
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }

}